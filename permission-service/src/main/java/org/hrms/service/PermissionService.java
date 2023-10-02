package org.hrms.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.hrms.dto.request.StatusRequestDto;
import org.hrms.dto.request.CreateDayOffRequestDto;
import org.hrms.dto.request.IsPermissionEligableRequestDto;
import org.hrms.dto.response.CreateDayOffResponseDto;
import org.hrms.exception.ErrorType;
import org.hrms.exception.PermissionManagerException;
import org.hrms.manager.IUserManager;
import org.hrms.mapper.IPermissionMapper;
import org.hrms.repository.IPermissionRepository;
import org.hrms.repository.entity.Permission;
import org.hrms.repository.enums.ApprovalStatus;
import org.hrms.repository.enums.EUserType;
import org.hrms.utility.JwtTokenManager;
import org.hrms.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class PermissionService extends ServiceManager<Permission, Long> {

    private final IPermissionRepository repository;

    private final IUserManager userManager;
    private final JwtTokenManager jwtTokenManager;

    public PermissionService(IPermissionRepository repository, IUserManager userManager, JwtTokenManager jwtTokenManager) {
        super(repository);
        this.repository = repository;
        this.userManager = userManager;
        this.jwtTokenManager = jwtTokenManager;
    }

//    public Boolean createPermission(CreatePermissionRequestDto dto) {
//        Permission permission = Permission.builder()
//                .authid(dto.getAuthid())
//                .typeOfPermit(dto.getTypeOfPermit())
//                .startDate(dto.getStartDate())
//                .endDate(dto.getEndDate())
//                .build();
//        permission.setNumberOfDays((int) ChronoUnit.DAYS.between(permission.getStartDate(), permission.getEndDate()));
//        System.out.println(permission.getAuthid());
//        try {
//            EUserType temp = userManager.getUserType(permission.getAuthid()).getBody();
//            System.out.println(temp);
//            if (temp.equals(EUserType.VISITOR)) {       //employee olarak değiştirilmeli test için visitor yaptım.
//                permission.setUserType(EUserType.VISITOR);
//                permission.setApprovalStatus(ApprovalStatus.PENDING);
//            } else if (temp.equals(EUserType.MANAGER)) {
//                permission.setUserType(EUserType.MANAGER);
//                permission.setApprovalStatus(ApprovalStatus.APPROVED);
//            } else {
//                throw new PermissionManagerException(ErrorType.GUEST_PERMISSION);
//            }
//        } catch (Exception e) {
//            throw new PermissionManagerException(ErrorType.PERSONEL_IS_NOT_EXIST);
//        }
//        permissionRepository.save(permission);
//        return true;
//    }


//    public List<PersonelPermissionResponseDto> getPermissionsAll() {
//        List<Permission> permissionList = permissionRepository.findAll();
//        List<PersonelPermissionResponseDto> dtoList = new ArrayList<>();
//        permissionList.forEach(permission -> {
//            PersonelPermissionResponseDto responseDto = IPermissionMapper.INSTANCE.toDto(permission);
//            String username = userManager.getUsername(permission.getAuthid()).getBody();
//            responseDto.setUsername(username);
//            dtoList.add(responseDto);
//        });
//
//        return dtoList;
//    }


//    public List<PersonelPermissionResponseDto> getPermissionByPerson(Long authid) {
//        List<Permission> permissionList = permissionRepository.findOptionalByAuthid(authid).get();
//        List<PersonelPermissionResponseDto> dtoList = new ArrayList<>();
//
//        permissionList.forEach(permission -> {
//            PersonelPermissionResponseDto responseDto = IPermissionMapper.INSTANCE.toDto(permission);
//            String username = userManager.getUsername(permission.getAuthid()).getBody();
//            responseDto.setUsername(username);
//            dtoList.add(responseDto);
//        });
//
//        return dtoList;
//    }


//    public String confirmPermission(Long permissionid, Boolean confirm) {
//        Optional<Permission> talep = permissionRepository.findById(permissionid);
//        if (talep.isEmpty()) {
//            throw new PermissionManagerException(ErrorType.PERMISSION_IS_NOT_EXIST);
//        }
//
//        if (confirm.equals(true)) {
//            talep.get().setApprovalStatus(ApprovalStatus.APPROVED);
//        } else {
//            talep.get().setApprovalStatus(ApprovalStatus.REJECTED);
//        }
//        update(talep.get());
//        return "Personel talep işlemi+ " + talep.get().getApprovalStatus() + " ile güncellendi";
//    }


    public CreateDayOffResponseDto createDayOff(CreateDayOffRequestDto dto) {
        if (!(dto.getUserType().equals(EUserType.MANAGER) || dto.getUserType().equals(EUserType.EMPLOYEE))) {
            throw new PermissionManagerException(ErrorType.INSUFFICIENT_PERMISSION);
        }
        if (dto.getStartDate().isAfter(dto.getEndDate()) || dto.getStartDate().isEqual(dto.getEndDate())) {
            throw new PermissionManagerException(ErrorType.INVALID_DATE);
        }

        Boolean isUSerValid = userManager.isDayOffRequestValid(IPermissionMapper.INSTANCE.toAuthIdAndCompanyNameCheckerRequestDto(dto)).getBody();
        if (!isUSerValid) {
            throw new PermissionManagerException(ErrorType.USER_NOT_VALID);
        }
        Boolean permissionEligable = isPermissionEligable(IsPermissionEligableRequestDto.builder().token(dto.getToken()).startDate(dto.getStartDate()).build());
        if (!permissionEligable) {
            throw new PermissionManagerException(ErrorType.PERMISSION_CONFLICT);
        }

        Permission permission = IPermissionMapper.INSTANCE.toPermission(dto);
        permission.setAuthid(jwtTokenManager.getIdFromToken(dto.getToken()).get());
        permission.setNumberOfDays(ChronoUnit.DAYS.between(permission.getStartDate(),permission.getEndDate()));
        Permission permission1 = statusSetter(dto.getUserType(), permission);
        save(permission1);
        return new CreateDayOffResponseDto("izin basariyla eklenmistir");
    }

    private Permission statusSetter(EUserType userType, Permission permission) {
        switch (userType) {
            case MANAGER -> {
                permission.setApprovalStatus(ApprovalStatus.APPROVED);
                permission.setReplyDate(LocalDate.now());
                return permission;
            }
            case EMPLOYEE -> {
                permission.setApprovalStatus(ApprovalStatus.PENDING);
                return permission;
            }
            default -> {
                throw new PermissionManagerException(ErrorType.BAD_REQUEST);
            }
        }
    }

    public Boolean isPermissionEligable(IsPermissionEligableRequestDto dto) {

        Optional<Long> optionalAuthId = jwtTokenManager.getIdFromToken(dto.getToken());
        if (optionalAuthId.isEmpty()) {
            throw new PermissionManagerException(ErrorType.INVALID_TOKEN);
        }

        Optional<List<Permission>> optionalPermissionList = repository.findByAuthid(optionalAuthId.get());
        if (optionalPermissionList.isEmpty()) {
            throw new PermissionManagerException(ErrorType.PERMISSION_IS_NOT_EXIST);
        }
        LocalDate newPermissionStartDate = dto.getStartDate();
        List<Permission> existingPermissions = optionalPermissionList.get();

        for (Permission existingPermission : existingPermissions) {
            LocalDate existingPermissionStartDate = existingPermission.getStartDate();
            LocalDate existingPermissionEndDate = existingPermission.getEndDate();

            if ((newPermissionStartDate.isAfter(existingPermissionStartDate) || newPermissionStartDate.isEqual(existingPermissionStartDate)) && newPermissionStartDate.isBefore(existingPermissionEndDate)) {
                throw new PermissionManagerException(ErrorType.PERMISSION_CONFLICT);
            }
        }

        return true;
    }

    public List<Permission> findDayOffByCompany(String companyName) {
        List<Permission> permissionList = repository.findByCompanyName(companyName);
        if (permissionList.size() < 1) {
            throw new PermissionManagerException(ErrorType.COMPANY_PERMISSION_NOT_EXISTS);
        }
        return permissionList;
    }

    public Boolean approveStatus(StatusRequestDto dto) {
        if (!dto.getUserType().equals(EUserType.MANAGER)) {
            throw new PermissionManagerException(ErrorType.INSUFFICIENT_PERMISSION);
        }

        Optional<Permission> optionalPermission = repository.findById(dto.getPermissionId());
        if (optionalPermission.isEmpty()) {
            throw new PermissionManagerException(ErrorType.BAD_REQUEST);
        }
        optionalPermission.get().setApprovalStatus(ApprovalStatus.APPROVED);
        optionalPermission.get().setReplyDate(LocalDate.now());
        update(optionalPermission.get());
        return true;
    }

    public Boolean denyStatus(StatusRequestDto dto) {
        if (!dto.getUserType().equals(EUserType.MANAGER)) {
            throw new PermissionManagerException(ErrorType.INSUFFICIENT_PERMISSION);
        }

        Optional<Permission> optionalPermission = repository.findById(dto.getPermissionId());
        if (optionalPermission.isEmpty()) {
            throw new PermissionManagerException(ErrorType.BAD_REQUEST);
        }
        optionalPermission.get().setApprovalStatus(ApprovalStatus.REJECTED);
        optionalPermission.get().setReplyDate(LocalDate.now());
        update(optionalPermission.get());
        return true;
    }
}


