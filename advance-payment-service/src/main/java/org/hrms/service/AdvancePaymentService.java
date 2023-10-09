package org.hrms.service;

import org.hrms.dto.request.AdvanceStatusRequestDto;
import org.hrms.dto.request.CreateAdvancePaymentRequestDto;
import org.hrms.dto.response.CreateAdvancePaymentResponseDto;
import org.hrms.exception.AdvancePaymentManagerException;
import org.hrms.exception.ErrorType;
import org.hrms.manager.IUserManager;
import org.hrms.mapper.IAdvancePaymentMapper;
import org.hrms.repository.IAdvancePaymentRepository;
import org.hrms.repository.entity.AdvancePayment;
import org.hrms.repository.enums.EStatus;
import org.hrms.repository.enums.EUserType;
import org.hrms.utility.JwtTokenManager;
import org.hrms.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AdvancePaymentService extends ServiceManager<AdvancePayment,Long> {

    private final IAdvancePaymentRepository repository;
    private final IUserManager userManager;
    private final JwtTokenManager jwtTokenManager;

    public AdvancePaymentService(IAdvancePaymentRepository repository, IUserManager userManager, JwtTokenManager jwtTokenManager) {
        super(repository);
        this.repository = repository;
        this.userManager = userManager;
        this.jwtTokenManager = jwtTokenManager;
    }

    public CreateAdvancePaymentResponseDto createAdvancePayment(CreateAdvancePaymentRequestDto dto) {

        if (!(dto.getUserType().equals(EUserType.MANAGER) || dto.getUserType().equals(EUserType.EMPLOYEE))) {
            throw new AdvancePaymentManagerException(ErrorType.INSUFFICIENT_PERMISSION);
        }
        Optional<Long> optionalAuthId = jwtTokenManager.getIdFromToken(dto.getToken());

        if (optionalAuthId.isEmpty()) {
            throw new AdvancePaymentManagerException(ErrorType.INVALID_TOKEN);
        }

        Boolean isRequestValid = userManager.isAdvanceRequestValid(IAdvancePaymentMapper.INSTANCE.toAdvancePaymentUserControlDto(dto)).getBody();
        if (!isRequestValid) {
            throw new AdvancePaymentManagerException(ErrorType.ADVANCE_REQUEST_NOT_VALID);
        }

        AdvancePayment advancePayment = IAdvancePaymentMapper.INSTANCE.toAdvancePayment(dto);
        advancePayment.setAuthid(optionalAuthId.get());

        AdvancePayment advancePayment1 = statusSetter(advancePayment, dto.getUserType());
        save(advancePayment1);
        if (advancePayment1.getStatus().equals(EStatus.APPROVED)) {
            return new CreateAdvancePaymentResponseDto("Avans isteginiz onaylandi!");
        }else{
        return new CreateAdvancePaymentResponseDto("Avans Istegi basariyla olusturuldu");
        }
    }

    private AdvancePayment statusSetter(AdvancePayment advancePayment, EUserType userType) {
        switch (userType) {
            case MANAGER -> {
                advancePayment.setStatus(EStatus.APPROVED);
                advancePayment.setReplyDate(LocalDate.now());
                return advancePayment;
            }
            case EMPLOYEE -> {
                advancePayment.setStatus(EStatus.PENDING);
                return advancePayment;
            }
            default -> {
                throw new AdvancePaymentManagerException(ErrorType.BAD_REQUEST);
            }
        }
    }

    public Boolean approveAdvancePaymentRequest(AdvanceStatusRequestDto dto) {
        if (!dto.getUserType().equals(EUserType.MANAGER)) {
            throw new AdvancePaymentManagerException(ErrorType.INSUFFICIENT_PERMISSION);
        }

        Optional<AdvancePayment> optionalAdvancePayment = findById(dto.getAdvanceId());
        if (optionalAdvancePayment.isEmpty()) {
            throw new AdvancePaymentManagerException(ErrorType.ADVANCE_REQUEST_NOT_FOUND);
        }
        optionalAdvancePayment.get().setStatus(EStatus.APPROVED);
        optionalAdvancePayment.get().setReplyDate(LocalDate.now());
        update(optionalAdvancePayment.get());
        return true;
    }

    public Boolean denyAdvancePaymentRequest(AdvanceStatusRequestDto dto) {
        if (!dto.getUserType().equals(EUserType.MANAGER)) {
            throw new AdvancePaymentManagerException(ErrorType.INSUFFICIENT_PERMISSION);
        }

        Optional<AdvancePayment> optionalAdvancePayment = findById(dto.getAdvanceId());
        if (optionalAdvancePayment.isEmpty()) {
            throw new AdvancePaymentManagerException(ErrorType.ADVANCE_REQUEST_NOT_FOUND);
        }
        optionalAdvancePayment.get().setStatus(EStatus.REJECTED);
        optionalAdvancePayment.get().setReplyDate(LocalDate.now());
        update(optionalAdvancePayment.get());
        return true;
    }

    public List<AdvancePayment> listAdvancePaymentRequests(String companyName) {
        List<AdvancePayment> advancePaymentList = repository.listPendingAdvanceRequests(companyName);
        if (advancePaymentList.size() < 1) {
            throw new AdvancePaymentManagerException(ErrorType.COMPANY_ADVANCE_PAYMENT_REQUEST_NOT_FOUND);
        }
        return advancePaymentList;
    }
}
