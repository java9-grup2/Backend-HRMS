package org.hrms.controller;
import lombok.RequiredArgsConstructor;
import org.hrms.dto.request.CreatePermissionRequestDto;
import org.hrms.dto.response.PersonelPermissionResponseDto;
import org.hrms.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public ResponseEntity<List<PersonelPermissionResponseDto>> getPermissionsAll() {
        return ResponseEntity.ok(permissionService.getPermissionsAll());
    }

    @GetMapping("/get-permissionsByPerson")
    public ResponseEntity<List<PersonelPermissionResponseDto>> getPermissionsByPerson(@RequestParam Long authid) {
        return ResponseEntity.ok(permissionService.getPermissionByPerson(authid));
    }


    @PutMapping("/confirmPermission")
    public ResponseEntity<String> confirmPermission(@RequestBody Long authid,Boolean confirm){
        return ResponseEntity.ok(permissionService.confirmPermission(authid,confirm));
    }




}

