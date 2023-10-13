package org.hrms.controller;

import lombok.RequiredArgsConstructor;
import org.hrms.dto.request.GeneralStatusRequestDto;
import org.hrms.dto.response.SuccessMessageResponseDto;
import org.hrms.rabbitmq.model.ActivateCompanyPackageModel;
import org.hrms.rabbitmq.model.CreateCompanyPackageModel;
import org.hrms.rabbitmq.model.DenyCompanyPackageModel;
import org.hrms.service.PackageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.hrms.constant.EndPoints.*;

@RestController
@RequestMapping(PACKAGE)
@RequiredArgsConstructor
public class PackageController {

    private final PackageService service;

    @GetMapping("apitest")
    public ResponseEntity<String> mesaj() {
        return ResponseEntity.ok("api calisiyor");
    }


    @PostMapping(SAVE)
    public ResponseEntity<SuccessMessageResponseDto> createPackage(@Valid @RequestBody CreateCompanyPackageModel model) {
        return ResponseEntity.ok(service.createPackage(model));
    }

    @PostMapping(ACTIVATESTATUS)
    public ResponseEntity<SuccessMessageResponseDto> activateStatus(@RequestBody ActivateCompanyPackageModel dto) {
        return ResponseEntity.ok(service.activateStatus(dto));
    }

    @PostMapping(DENYSTATUS)
    public ResponseEntity<SuccessMessageResponseDto> denyStatus(@Valid @RequestBody DenyCompanyPackageModel model) {
        return ResponseEntity.ok(service.denyStatus(model));
    }
}
