package org.hrms.controller;

import lombok.RequiredArgsConstructor;
import org.hrms.constant.EndPoints;
import org.hrms.dto.request.CreateFinancialPerformanceRequestDto;
import org.hrms.dto.request.FindSelectedYearRequestDto;
import org.hrms.repository.entity.FinancialPerformance;
import org.hrms.service.FinancialPerformanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.hrms.constant.EndPoints.*;

@RestController
@RequestMapping(FINANCIALPERFORMANCE)
@RequiredArgsConstructor
public class FinancialPerformanceController {

    private final FinancialPerformanceService service;

    @PostMapping(SAVE)
    public ResponseEntity<Boolean> create(@RequestBody @Valid CreateFinancialPerformanceRequestDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping(FINDALL)
    public ResponseEntity<List<FinancialPerformance>> listCompanyFinancialPerformance(String companyName) {
        return ResponseEntity.ok(service.listCompanyFinancialPerformance(companyName));
    }

    @GetMapping(FINDSELECTEDYEAR)
    public ResponseEntity<FinancialPerformance> findSelectedYear(@Valid FindSelectedYearRequestDto dto) {
        return ResponseEntity.ok(service.findSelectedYear(dto));
    }

    @PostMapping(CALCULATETOTALEXPENSES)
    public ResponseEntity<Double> calculateTotalExpensesForCompany(@RequestBody String companyName) {
        Double totalExpenses = service.calculateTotalExpensesForCompany(companyName);
        return ResponseEntity.ok(totalExpenses);
    }
}
