package org.hrms.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.hrms.dto.request.CreatePermissionRequestDto;
import org.hrms.dto.request.UpdateStatusRequestDto;
import org.hrms.dto.response.WorkerPermissionForManager;
import org.hrms.dto.response.WorkerPermissionForWorker;
import org.hrms.repository.IPermissionRepository;
import org.hrms.repository.entity.Permission;
import org.hrms.utility.ServiceManager;
import org.springframework.stereotype.Service;
import static org.hrms.repository.enums.ApprovalStatus.PENDING_APPROVAL;

@Service
public class PermissionService extends ServiceManager<Permission, Long> {

    private final IPermissionRepository permissionRepository;

    public PermissionService(IPermissionRepository permissionRepository) {
        super(permissionRepository);
        this.permissionRepository = permissionRepository;
    }

    public boolean createPermission(CreatePermissionRequestDto createPermissionRequestDto) {
        Permission permission = Permission.builder()
                .approvalStatus(PENDING_APPROVAL)
                .dateOfRequest(LocalDate.now())
                .endDate(createPermissionRequestDto.getEndDate())
                .managerid(createPermissionRequestDto.getManagerid())
                .workerid(createPermissionRequestDto.getWorkerid())
                .startDate(createPermissionRequestDto.getStartDate())
                .typeOfPermit(createPermissionRequestDto.getTypeOfPermit())
                .numberOfDays(createPermissionRequestDto.getNumberOfDays())
                .name(createPermissionRequestDto.getName())
                .surname(createPermissionRequestDto.getSurname())
                .build();
        save(permission);
        return true;
    }

    public List<WorkerPermissionForWorker> getPermissionsForWorker(Long workerid) {
        List<Permission> permission = permissionRepository.findOptionalByWorkerid(workerid);
        List<WorkerPermissionForWorker> workers = new ArrayList<>();
        permission.forEach(x -> {
            workers.add(WorkerPermissionForWorker.builder()
                    .dateOfRequest(x.getDateOfRequest())
                    .approvalStatus(x.getApprovalStatus())
                    .startDate(x.getStartDate())
                    .endDate(x.getEndDate())
                    .typeOfPermit(x.getTypeOfPermit())
                    .numberOfDays(x.getNumberOfDays())
                    .replyDate(x.getReplyDate())
                    .id(x.getId())
                    .build());
        });
        return workers;
    }
    public List<WorkerPermissionForManager> getPermissionForManager(Long managerid) {
        List<Permission> permissions = permissionRepository.findOptionalByManagerid(managerid);
        List<WorkerPermissionForManager> managers = new ArrayList<>();
        permissions.forEach(x -> {
            managers.add(WorkerPermissionForManager.builder()
                    .dateOfRequest(x.getDateOfRequest())
                    .approvalStatus(x.getApprovalStatus())
                    .startDate(x.getStartDate())
                    .endDate(x.getEndDate())
                    .typeOfPermit(x.getTypeOfPermit())
                    .numberOfDays(x.getNumberOfDays())
                    .replyDate(x.getReplyDate())
                    .id(x.getId())
                    .name(x.getName())
                    .surname(x.getSurname())
                    .build());
        });
        return managers;
    }

    public Boolean updateStatus(UpdateStatusRequestDto updateStatusRequestDto) {
        Optional<Permission> permission = findById(updateStatusRequestDto.getId());
        permission.get().setApprovalStatus(updateStatusRequestDto.getStatus());
        permission.get().setReplyDate(LocalDate.now());
        update(permission.get());
        return true;
    }

}


