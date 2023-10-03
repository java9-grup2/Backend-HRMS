package org.hrms.service;

import org.hrms.dto.request.CreateExpenseRequestDto;
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


    private Expense statusSetter(EUserType userType, Expense expense) {
        switch (userType) {
            case MANAGER -> {
                expense.setApprovalStatus(ApprovalStatus.APPROVED);
                return expense;
            }
            case EMPLOYEE -> {
                expense.setApprovalStatus(ApprovalStatus.PENDING);
                return expense;
            }
            default -> {
                throw new ExpenseManagerException(ErrorType.BAD_REQUEST);
            }
        }
    }

}
