package org.hrms.Controller;

import lombok.RequiredArgsConstructor;
import org.hrms.dto.request.IsCompanyRequestValidDto;
import org.hrms.dto.request.PublicHolidayCompanyRequestDto;
import org.hrms.dto.request.UpdateCompanyRequestDto;
import org.hrms.dto.response.CompanyUpdateResponseDto;
import org.hrms.dto.response.ContactInformationResponseDto;
import org.hrms.rabbitmq.model.ActivateCompanyStatusModel;
import org.hrms.rabbitmq.model.CreateCompanyModel;
import org.hrms.rabbitmq.model.DeleteCompanyByRegisterDenyModel;
import org.hrms.rabbitmq.model.IncreaseCompanyWorkerModel;
import org.hrms.repository.entity.Company;
import org.hrms.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping(FINDALL2)
    public ResponseEntity<List<Company>> findAllCompanies2() {
        return ResponseEntity.ok(service.findAll2());
    }

    @GetMapping(FINDBYID)
    public ResponseEntity<Company> findCompanyById(Long id) {
        return ResponseEntity.ok(service.findCompanyById(id));
    }
    @GetMapping(FINDBYID2)
    public ResponseEntity<Company> findCompanyById2(Long id) {
        return ResponseEntity.ok(service.findCompanyById2(id));
    }

    @GetMapping(FINDBYCOMPANYNAME)
    public ResponseEntity<Company> findCompanyByCompanyName(String companyName) {
        return ResponseEntity.ok(service.findCompanyByCompanyName(companyName));
    }


    @PutMapping(UPDATE)
    public ResponseEntity<CompanyUpdateResponseDto> updateCompanyDetails(@Valid @RequestBody UpdateCompanyRequestDto dto) {
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


    @DeleteMapping(DELETEBYCOMPANYNAME)
    public ResponseEntity<Boolean> deleteByCompanyName(@RequestBody DeleteCompanyByRegisterDenyModel model) {
        return ResponseEntity.ok(service.deleteByCompanyName(model));
    }

    @PutMapping(ACTIVATESTATUS)
    public ResponseEntity<Boolean> activateCompanyStatus(@RequestBody ActivateCompanyStatusModel model) {
        return ResponseEntity.ok(service.activateCompanyStatus(model));
    }

    @PostMapping(COMPANYREQUESTCHECKER)
    public ResponseEntity<Boolean> isCompanyRequestValid(@RequestBody IsCompanyRequestValidDto dto) {
        return ResponseEntity.ok(service.isCompanyRequestValid(dto));
    }

    @PutMapping(INCREASECOMPANYWORKER)
    public ResponseEntity<Boolean> increaseCompanyWorker(@RequestBody IncreaseCompanyWorkerModel model) {
        return ResponseEntity.ok(service.increaseCompanyWorker(model));
    }

    @GetMapping(CONTACTINFORMATION)
    public ResponseEntity<ContactInformationResponseDto> getContactInformation(@RequestParam String companyName) {
        return ResponseEntity.ok(service.getContactInformation(companyName));
    }
}
