package org.hrms.service;

import org.hrms.dto.request.CreateShiftsAndBreaksRequestDto;
import org.hrms.exception.ErrorType;
import org.hrms.exception.ShiftsAndBreaksException;
import org.hrms.manager.ICompanyManager;
import org.hrms.mapper.IShiftAndBreaksMapper;
import org.hrms.repository.IShiftsAndBreaksRepository;
import org.hrms.repository.entity.ShiftsAndBreaks;
import org.hrms.repository.enums.EUserType;
import org.hrms.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class ShiftsAndBreaksService extends ServiceManager<ShiftsAndBreaks,Long> {

    private final IShiftsAndBreaksRepository repository;
    private final ICompanyManager companyManager;

    public ShiftsAndBreaksService(IShiftsAndBreaksRepository repository, ICompanyManager companyManager) {
        super(repository);
        this.repository = repository;
        this.companyManager = companyManager;
    }

    public Boolean createShiftsAndBreaks(CreateShiftsAndBreaksRequestDto dto) {
        if (!dto.getUserType().equals(EUserType.MANAGER)) {
            throw new ShiftsAndBreaksException(ErrorType.INSUFFICIENT_PERMISSION);
        }

        boolean existByShiftType = repository.existsByCompanyNameAndShiftTypes(dto.getCompanyName(), dto.getShiftTypes());
        if (existByShiftType) {
            throw new ShiftsAndBreaksException(ErrorType.SHIFT_TYPE_ALREADY_EXISTS);
        }

        Boolean isCompanyExists = companyManager.isCompanyExists(dto.getCompanyName()).getBody();

        if (!isCompanyExists) {
            throw new ShiftsAndBreaksException(ErrorType.COMPANY_NOT_FOUND);
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            save(IShiftAndBreaksMapper.INSTANCE.toShiftsAndBreaks(dto));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
