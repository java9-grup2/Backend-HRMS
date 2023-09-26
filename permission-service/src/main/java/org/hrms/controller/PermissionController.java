package org.hrms.controller;
import lombok.RequiredArgsConstructor;
import org.hrms.dto.request.CreatePermissionRequestDto;
import org.hrms.dto.request.UpdateStatusRequestDto;
import org.hrms.dto.response.WorkerPermissionForManager;
import org.hrms.dto.response.WorkerPermissionForWorker;
import org.hrms.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.hrms.constant.EndPoints.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(API+VERSION+PERMISSION)
public class PermissionController {

    private final PermissionService permissionService;
    @PostMapping("/create-permission")
    public ResponseEntity<?>createPermission(@RequestBody CreatePermissionRequestDto createPermissionRequestDto){
        return ResponseEntity.ok(permissionService.createPermission(createPermissionRequestDto));
    }
    @GetMapping("/worker/{workerid}")
    public ResponseEntity<List<WorkerPermissionForWorker>> getPermissionsForWorker(@PathVariable Long workerid){
        return ResponseEntity.ok(permissionService.getPermissionsForWorker(workerid));
    }
    @GetMapping("/manager/{managerid}")
    public ResponseEntity<List<WorkerPermissionForManager>> getPermissionsForManager(@PathVariable Long managerid){
        return ResponseEntity.ok(permissionService.getPermissionForManager(managerid));
    }
    @PutMapping("/update")
    public  ResponseEntity<?> updateStatus(@RequestBody UpdateStatusRequestDto updateStatusRequestDto){
        return ResponseEntity.ok(permissionService.updateStatus(updateStatusRequestDto));
    }

}

