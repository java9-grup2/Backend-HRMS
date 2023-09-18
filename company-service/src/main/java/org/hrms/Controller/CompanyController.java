package org.hrms.Controller;

import lombok.RequiredArgsConstructor;
import org.hrms.constant.EndPoints;
import org.hrms.dto.request.SaveCompanyRequestDto;
import org.hrms.rabbitmq.model.CreateCompanyModel;
import org.hrms.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.hrms.constant.EndPoints.*;

@RestController
@RequestMapping(COMPANY)
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService service;


    @PostMapping(SAVE)
    public ResponseEntity<Boolean> createCompany(CreateCompanyModel model) {
        return ResponseEntity.ok(service.createCompany(model));
    }

    @PostMapping(ISCOMPANYEXISTS)
    public ResponseEntity<Boolean> isCompanyExists(@RequestParam String companyName) {
        return ResponseEntity.ok(service.IsCompanyExists(companyName));
    }

    @GetMapping("message")

    public ResponseEntity<String> message() {
        return ResponseEntity.ok("sa");
    }
}
