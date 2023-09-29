package org.hrms.service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.hrms.dto.request.CreatePermissionRequestDto;
import org.hrms.exception.ErrorType;
import org.hrms.exception.PermissionManagerException;
import org.hrms.manager.IUserManager;
import org.hrms.repository.IPermissionRepository;
import org.hrms.repository.entity.Permission;
import org.hrms.repository.enums.ApprovalStatus;
import org.hrms.repository.enums.EUserType;
import org.hrms.utility.ServiceManager;
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
        permission.setNumberOfDays((int) ChronoUnit.DAYS.between(permission.getEndDate(), permission.getStartDate()));
        try {
            EUserType temp = userManager.getUserType(permission.getAuthid()).getBody();
            if (temp.equals(EUserType.EMPLOYEE)) {
                permission.setUserType(EUserType.EMPLOYEE);
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
//    public List<PersonelPermissionResponseDto> getPermissionsAll2() {
//        List<Permission> permissions = permissionRepository.findAll();
//        List<PersonelPermissionResponseDto> permissionDtos = new ArrayList<>();
//
//        for (Permission permission : permissions) {
//            PersonelPermissionResponseDto dto = new PersonelPermissionResponseDto();
//            dto.setId(permission.getId());
//            //bu kodu cuma günü çözücem
//            //dto.setUsername(userManager.getUsername(permission.getAuthid()).getBody());
//            dto.setStartDate(permission.getStartDate());
//            dto.setEndDate(permission.getEndDate());
//            dto.setTypeOfPermit(permission.getTypeOfPermit());
//            dto.setApprovalStatus(permission.getApprovalStatus());
//
//            permissionDtos.add(dto);
//        }
//
//        return permissionDtos;
//    }


    //    public List<PersonelPermissionResponseDto> getPermissionsAll2() {
//        List<Permission> permissionList = permissionRepository.findAll();
//        List<PersonelPermissionResponseDto> dtoList = new ArrayList<>();
//        permissionList.forEach(permission -> {
//            PersonelPermissionResponseDto responseDto = IPermissionMapper.INSTANCE.entityToDto(permission);
//            //String username = userManager.getUsername(permission.getAuthid()).getBody();
//            //responseDto.setUsername(username);
//            dtoList.add(responseDto);
//        });
//
//        return dtoList;
//    }
    public List<Permission> getPermissionsAll() {
        List<Permission> permissionList = permissionRepository.findAll();
        return permissionList;
    }


    public Optional<List<Permission>> getPermissionByPerson(Long authid) {
        Optional<List<Permission>> permissionList = permissionRepository.findOptionalByAuthid(authid);
        return permissionList;
    }

//    public List<PersonelPermissionResponseDto> getPermissionByPerson2(Long authid) {
//        List<Permission> permissionList = permissionRepository.findOptionalByAuthid(authid);
//        List<PersonelPermissionResponseDto> dtoList = new ArrayList<>();
//        permissionList.forEach(person -> {
//            dtoList.add(PersonelPermissionResponseDto.builder()
//                    .id(person.getId())
//                    //.username(userManager.getUsername(person.getAuthid()).getBody())
//                    .startDate(person.getStartDate())
//                    .endDate(person.getEndDate())
//                    .typeOfPermit(person.getTypeOfPermit())
//                    .approvalStatus(person.getApprovalStatus())
//                    .build());
//        });
//        return dtoList;
//    }

//    public List<PersonelPermissionResponseDto> getPermissionByPerson2(Long authid) {
//        List<Permission> permissionList = permissionRepository.findOptionalByAuthid(authid);
//        List<PersonelPermissionResponseDto> dtoList = new ArrayList<>();
//
//        permissionList.forEach(permission -> {
//            PersonelPermissionResponseDto responseDto = IPermissionMapper.INSTANCE.entityToDto(permission);
//            //String username = userManager.getUsername(permission.getAuthid()).getBody();
//            //responseDto.setUsername(username);
//            dtoList.add(responseDto);
//        });
//
//        return dtoList;
//    }


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
        return "Personel talep işlemi başarıyla sonuçlandı";
    }


}


