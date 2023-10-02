package org.hrms.controller;
import lombok.RequiredArgsConstructor;
import org.hrms.dto.request.StatusRequestDto;
import org.hrms.dto.request.CreateDayOffRequestDto;
import org.hrms.dto.request.IsPermissionEligableRequestDto;
import org.hrms.dto.response.CreateDayOffResponseDto;
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

    @PutMapping(APPROVESTATUS)
    public ResponseEntity<Boolean> approveStatus(@RequestBody StatusRequestDto dto) {
        return ResponseEntity.ok(service.approveStatus(dto));
    }

    @PutMapping(DENYSTATUS)
    public ResponseEntity<Boolean> denyStatus(@RequestBody StatusRequestDto dto) {
        return ResponseEntity.ok(service.denyStatus(dto));
    }
}

