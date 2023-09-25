package org.hrms.Controller;

import lombok.RequiredArgsConstructor;
import org.hrms.dto.request.PublicHolidayCompanyRequestDto;
import org.hrms.dto.request.UpdateCompanyRequestDto;
import org.hrms.rabbitmq.model.CreateCompanyModel;
import org.hrms.repository.entity.Company;
import org.hrms.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(FINDALL)
    public ResponseEntity<List<Company>> findAllCompanies() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(FINDBYID)
    public ResponseEntity<Company> findCompanyById(Long id) {
        return ResponseEntity.ok(service.findCompanyById(id));
    }

    @GetMapping(FINDBYCOMPANYNAME)
    public ResponseEntity<Company> findCompanyByCompanyName(String companyName) {
        return ResponseEntity.ok(service.findCompanyByCompanyName(companyName));
    }


    @PutMapping(UPDATE)
    public ResponseEntity<Boolean> updateCompanyDetails(@RequestBody UpdateCompanyRequestDto dto) {
        return ResponseEntity.ok(service.updateCompanyDetailsByManager(dto));
    }

    @DeleteMapping(DELETEBYID)
    public ResponseEntity<Boolean> deleteCompanyById(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteCompanyById(id));
    }

    @GetMapping(PUBLICHOLIDAY)
    public ResponseEntity<List<PublicHolidayCompanyRequestDto>> getPublicHoliday(){
        return ResponseEntity.ok(service.getPublicHoliday());
    }





}
