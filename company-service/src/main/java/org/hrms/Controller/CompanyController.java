package org.hrms.Controller;

import lombok.RequiredArgsConstructor;
import org.hrms.dto.request.SaveCompanyRequestDto;
import org.hrms.rabbitmq.model.CreateCompanyModel;
import org.hrms.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.hrms.constant.EndPoints.COMPANY;
import static org.hrms.constant.EndPoints.SAVE;

@RestController
@RequestMapping(COMPANY)
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService service;


    @PostMapping(SAVE)
    public ResponseEntity<Boolean> createCompany(CreateCompanyModel model) {
        return ResponseEntity.ok(service.createCompany(model));
    }

    @GetMapping("message")
    public ResponseEntity<String> message() {
        return ResponseEntity.ok("sa");
    }
}
