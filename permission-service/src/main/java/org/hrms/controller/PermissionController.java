package org.hrms.controller;
import lombok.RequiredArgsConstructor;
import org.hrms.dto.request.CreateDayOffRequestDto;
import org.hrms.dto.request.CreatePermissionRequestDto;
import org.hrms.dto.request.IsPermissionEligableRequestDto;
import org.hrms.dto.response.CreateDayOffResponseDto;
import org.hrms.dto.response.PersonelPermissionResponseDto;
import org.hrms.repository.entity.Permission;
import org.hrms.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.hrms.constant.EndPoints.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(PERMISSION)
public class PermissionController {

    private final PermissionService service;
//    @PostMapping("/create-permission")
//    public ResponseEntity<Boolean>createPermission(@RequestBody CreatePermissionRequestDto createPermissionRequestDto){
//        return ResponseEntity.ok(permissionService.createPermission(createPermissionRequestDto));
//    }

    @PostMapping(SAVE)
    public ResponseEntity<CreateDayOffResponseDto> createDayOff(@RequestBody CreateDayOffRequestDto dto) {
        return ResponseEntity.ok(service.createDayOff(dto));
    }

    @GetMapping(FINDBYAUTHID)
    public ResponseEntity<Boolean> isPermissionEligable(@RequestBody IsPermissionEligableRequestDto dto) {
        return ResponseEntity.ok(service.isPermissionEligable(dto));
    }

    @GetMapping(FINDDAYOFFBYCOMPANY)
    public ResponseEntity<List<Permission>> findDayOffByCompany(@RequestParam String companyName){
        return ResponseEntity.ok(service.findDayOffByCompany(companyName));
    }


//    @GetMapping("/get-permissionsAll")
//    public ResponseEntity<List<PersonelPermissionResponseDto>> getPermissionsAll() {
//        return ResponseEntity.ok(service.getPermissionsAll());
//    }

//    @GetMapping("/get-permissionsByPerson")
//    public ResponseEntity<List<PersonelPermissionResponseDto>> getPermissionsByPerson(@RequestParam Long authid) {
//        return ResponseEntity.ok(service.getPermissionByPerson(authid));
//    }
//
//
//    @PutMapping("/confirmPermission")
//    public ResponseEntity<String> confirmPermission(@RequestBody Long authid,Boolean confirm){
//        return ResponseEntity.ok(service.confirmPermission(authid,confirm));
//    }


}

