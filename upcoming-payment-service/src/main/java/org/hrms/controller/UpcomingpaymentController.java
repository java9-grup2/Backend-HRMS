package org.hrms.controller;

import lombok.RequiredArgsConstructor;
import org.hrms.dto.CompanyNameToStringDto;
import org.hrms.repository.Upcomingpayment;
import org.hrms.service.UpcomingpaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hrms.constant.EndPoints.*;

@RestController
@RequestMapping({"api/v1/upcoming"})
@RequiredArgsConstructor
public class UpcomingpaymentController {

    private final UpcomingpaymentService service;



//    @PostMapping({"/savecompany"})
//    public ResponseEntity<Boolean> createNewCompany(@RequestBody CompanySaveToUpcomingDto dto) {
//        return ResponseEntity.ok(this.service.createNewCompany(dto));
//    }

    @GetMapping( SAVECOMPANY)
    public ResponseEntity<List<Upcomingpayment>> findAll() {
        return ResponseEntity.ok(this.service.findAll());
    }

    @PostMapping(CREATENEWUPCOMİNGPAYMENT)
    public ResponseEntity<Upcomingpayment> createNewCompanyPayment(@RequestBody Upcomingpayment upcomingpayment) {
        return ResponseEntity.ok((Upcomingpayment) this.service.save(upcomingpayment));
    }

    @GetMapping(FINDALLWİTHCOMPANY)
    public ResponseEntity<List<Upcomingpayment>> findAllWithCompany(@RequestParam String companyName) {
        return ResponseEntity.ok(this.service.findAllWithCompany(companyName));
    }

    @DeleteMapping(DELETEBYID)
    public ResponseEntity<Boolean> deletePaymentByid(@PathVariable Long id) {
        return ResponseEntity.ok(this.service.deletePaymentById(id));
    }
}