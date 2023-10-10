package org.hrms.controller;

import lombok.RequiredArgsConstructor;
import org.hrms.service.PackageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
