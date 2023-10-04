package org.hrms.service;

import org.hrms.dto.request.CreateExpenseRequestDto;
import org.hrms.dto.request.StatusRequestDto;
import org.hrms.dto.response.CreateExpenseResponseDto;
import org.hrms.exception.ErrorType;
import org.hrms.exception.ExpenseManagerException;
import org.hrms.manager.IUserManager;
import org.hrms.mapper.IExpenseMapper;
import org.hrms.repository.IExpenseRepository;
import org.hrms.repository.entity.Expense;
import org.hrms.repository.enums.ApprovalStatus;
import org.hrms.repository.enums.EUserType;
import org.hrms.utility.JwtTokenManager;
import org.hrms.utility.ServiceManager;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class ExpenseService extends ServiceManager<Expense,Long> {
    private final IExpenseRepository repository;
    private final JwtTokenManager jwtTokenManager;

    private final IUserManager userManager;

    public ExpenseService(IExpenseRepository repository, JwtTokenManager jwtTokenManager, IUserManager userManager) {
        super(repository);
        this.repository = repository;
        this.jwtTokenManager = jwtTokenManager;
        this.userManager = userManager;
    }

    public CreateExpenseResponseDto createExpense(CreateExpenseRequestDto dto){
        if (!(dto.getUserType().equals(EUserType.MANAGER) || dto.getUserType().equals(EUserType.EMPLOYEE))) {
            throw new ExpenseManagerException(ErrorType.INSUFFICIENT_PERMISSION);
        }

        Boolean isUserValid = userManager.isExpenseRequestValid(IExpenseMapper.INSTANCE.toAuthIdAndCompanyNameCheckerRequestDto(dto)).getBody();
        if (!isUserValid) {
            throw new ExpenseManagerException(ErrorType.USER_NOT_VALID);
        }

        Expense expense= IExpenseMapper.INSTANCE.toExpense(dto);
        expense.setAuthid(jwtTokenManager.getIdFromToken(dto.getToken()).get());

        Expense expense1 = statusSetter(dto.getUserType(), expense);
        save(expense1);
        return new CreateExpenseResponseDto("Harcama talebi başarıyla eklendi");
    }

    // Kullanıcı türüne göre bir gider nesnesinin onay durumunu ayarlar ve döndürür.
    private Expense statusSetter(EUserType userType, Expense expense) {
        switch (userType) {
            case MANAGER -> {
                // Yönetici tarafından onaylandıysa onay durumunu "ONAYLANDI" olarak ayarlar.
                expense.setApprovalStatus(ApprovalStatus.APPROVED);
                expense.setReplyDate(LocalDate.now());
                return expense;
            }
            case EMPLOYEE -> {
                // Çalışan tarafından oluşturulduysa onay durumunu "BEKLEMEDE" olarak ayarlar.
                expense.setApprovalStatus(ApprovalStatus.PENDING);
                return expense;
            }
            default -> {
                // Geçersiz kullanıcı türü verildiğinde bir hata fırlatır.
                throw new ExpenseManagerException(ErrorType.BAD_REQUEST);
            }
        }
    }

    // Yönetici tarafından bir giderin onay durumunu "ONAYLANDI" olarak ayarlar.
    public Boolean approveStatus(StatusRequestDto dto) {
        if (!dto.getUserType().equals(EUserType.MANAGER)) {
            // Yönetici olmayan bir kullanıcı tarafından işlem yapılması durumunda bir hata fırlatır.
            throw new ExpenseManagerException(ErrorType.INSUFFICIENT_PERMISSION);
        }

        Optional<Expense> optionalExpense = repository.findById(dto.getExpenseId());
        if (optionalExpense.isEmpty()) {
            // Belirtilen gider kimliğiyle gider bulunamazsa bir hata fırlatır.
            throw new ExpenseManagerException(ErrorType.BAD_REQUEST);
        }
        optionalExpense.get().setApprovalStatus(ApprovalStatus.APPROVED);
        optionalExpense.get().setReplyDate(LocalDate.now());
        update(optionalExpense.get());
        return true;
    }

    // Yönetici tarafından bir giderin onay durumunu "REDDEDİLDİ" olarak ayarlar.
    public Boolean denyStatus(StatusRequestDto dto) {
        if (!dto.getUserType().equals(EUserType.MANAGER)) {
            // Yönetici olmayan bir kullanıcı tarafından işlem yapılması durumunda bir hata fırlatır.
            throw new ExpenseManagerException(ErrorType.INSUFFICIENT_PERMISSION);
        }

        Optional<Expense> optionalExpense = repository.findById(dto.getExpenseId());
        if (optionalExpense.isEmpty()) {
            // Belirtilen gider kimliğiyle gider bulunamazsa bir hata fırlatır.
            throw new ExpenseManagerException(ErrorType.BAD_REQUEST);
        }
        optionalExpense.get().setApprovalStatus(ApprovalStatus.REJECTED);
        optionalExpense.get().setReplyDate(LocalDate.now());
        update(optionalExpense.get());
        return true;
    }

    // Belirli bir şirkete ait tüm giderleri bulur ve listeler.
    public List<Expense> findExpenseByCompany(String companyName) {
        List<Expense> expenseList = repository.findByCompanyName(companyName);
        if (expenseList.size() < 1) {
            // Belirli bir şirkete ait giderler bulunamazsa bir hata fırlatır.
            throw new ExpenseManagerException(ErrorType.COMPANY_EXPENSE_NOT_EXISTS);
        }
        return expenseList;
    }
}
