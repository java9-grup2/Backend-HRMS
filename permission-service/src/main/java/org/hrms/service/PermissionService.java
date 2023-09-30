package org.hrms.service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hrms.dto.request.CreatePermissionRequestDto;
import org.hrms.dto.response.PersonelPermissionResponseDto;
import org.hrms.exception.ErrorType;
import org.hrms.exception.PermissionManagerException;
import org.hrms.manager.IUserManager;
import org.hrms.mapper.IPermissionMapper;
import org.hrms.repository.IPermissionRepository;
import org.hrms.repository.entity.Permission;
import org.hrms.repository.enums.ApprovalStatus;
import org.hrms.repository.enums.EUserType;
import org.hrms.utility.ServiceManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PermissionService extends ServiceManager<Permission, Long> {

    private final IPermissionRepository permissionRepository;

    private final IUserManager userManager;

    public PermissionService(IPermissionRepository permissionRepository, IUserManager userManager) {
        super(permissionRepository);
        this.permissionRepository = permissionRepository;
        this.userManager = userManager;
    }

    public Boolean createPermission(CreatePermissionRequestDto dto) {
        Permission permission = Permission.builder()
                .authid(dto.getAuthid())
                .typeOfPermit(dto.getTypeOfPermit())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
        permission.setNumberOfDays((int) ChronoUnit.DAYS.between(permission.getStartDate(), permission.getEndDate()));
        System.out.println(permission.getAuthid());
        try {
            EUserType temp = userManager.getUserType(permission.getAuthid()).getBody();
            System.out.println(temp);
            if (temp.equals(EUserType.VISITOR)) {       //employee olarak değiştirilmeli test için visitor yaptım.
                permission.setUserType(EUserType.VISITOR);
                permission.setApprovalStatus(ApprovalStatus.PENDING);
            } else if (temp.equals(EUserType.MANAGER)) {
                permission.setUserType(EUserType.MANAGER);
                permission.setApprovalStatus(ApprovalStatus.APPROVED);
            } else {
                throw new PermissionManagerException(ErrorType.GUEST_PERMISSION);
            }
        } catch (Exception e) {
            throw new PermissionManagerException(ErrorType.PERSONEL_IS_NOT_EXIST);
        }
        permissionRepository.save(permission);
        return true;
    }


    public List<PersonelPermissionResponseDto> getPermissionsAll() {
        List<Permission> permissionList = permissionRepository.findAll();
        List<PersonelPermissionResponseDto> dtoList = new ArrayList<>();
        permissionList.forEach(permission -> {
            PersonelPermissionResponseDto responseDto = IPermissionMapper.INSTANCE.toDto(permission);
            String username = userManager.getUsername(permission.getAuthid()).getBody();
            responseDto.setUsername(username);
            dtoList.add(responseDto);
        });

        return dtoList;
    }


    public List<PersonelPermissionResponseDto> getPermissionByPerson(Long authid) {
        List<Permission> permissionList = permissionRepository.findOptionalByAuthid(authid).get();
        List<PersonelPermissionResponseDto> dtoList = new ArrayList<>();

        permissionList.forEach(permission -> {
            PersonelPermissionResponseDto responseDto = IPermissionMapper.INSTANCE.toDto(permission);
            String username = userManager.getUsername(permission.getAuthid()).getBody();
            responseDto.setUsername(username);
            dtoList.add(responseDto);
        });

        return dtoList;
    }


    public String confirmPermission(Long permissionid, Boolean confirm) {
        Optional<Permission> talep = permissionRepository.findById(permissionid);
        if (talep.isEmpty()) {
            throw new PermissionManagerException(ErrorType.PERMISSION_IS_NOT_EXIST);
        }

        if (confirm.equals(true)) {
            talep.get().setApprovalStatus(ApprovalStatus.APPROVED);
        } else {
            talep.get().setApprovalStatus(ApprovalStatus.REJECTED);
        }
        update(talep.get());
        return "Personel talep işlemi+ " + talep.get().getApprovalStatus() + " ile güncellendi";
    }


}


