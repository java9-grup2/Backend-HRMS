package org.hrms.controller;
import lombok.RequiredArgsConstructor;
import org.hrms.dto.request.CreatePermissionRequestDto;
import org.hrms.repository.entity.Permission;
import org.hrms.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import static org.hrms.constant.EndPoints.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(PERMISSION)
public class PermissionController {

    private final PermissionService permissionService;
    @PostMapping("/create-permission")
    public ResponseEntity<Boolean>createPermission(@RequestBody CreatePermissionRequestDto createPermissionRequestDto){
        return ResponseEntity.ok(permissionService.createPermission(createPermissionRequestDto));
    }

    @GetMapping("/get-permissionsAll")
    public ResponseEntity<List<Permission>> getPermissionsAll() {
        return ResponseEntity.ok(permissionService.getPermissionsAll());
    }
    //    @GetMapping("/get-permissionsAll2")
//    public ResponseEntity<List<PersonelPermissionResponseDto>> getPermissionsAll2() {
//        return ResponseEntity.ok(permissionService.getPermissionsAll2());
//    }
//    @GetMapping("/get-permissionsAll2")
//    public ResponseEntity<List<Permission>> getPermissionsAll2() {
//        return ResponseEntity.ok(permissionService.getPermissionsAll2());
//    }
    @GetMapping("/get-permissionsByPerson")
    public ResponseEntity<Optional<List<Permission>>> getPermissionsByPerson(@RequestParam Long authid) {
        return ResponseEntity.ok(permissionService.getPermissionByPerson(authid));
    }
//    @GetMapping("/get-permissionsByPerson2")
//    public ResponseEntity<List<PersonelPermissionResponseDto>> getPermissionsByPerson2(@RequestBody Long authid) {
//        return ResponseEntity.ok(permissionService.getPermissionByPerson2(authid));
//    }

    @PutMapping("/confirmPermission")
    public ResponseEntity<String> confirmPermission(@RequestBody Long authid,Boolean confirm){
        return ResponseEntity.ok(permissionService.confirmPermission(authid,confirm));
    }




}

