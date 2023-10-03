package org.hrms.controller;

import lombok.RequiredArgsConstructor;

import org.hrms.dto.request.CreateExpenseRequestDto;
import org.hrms.dto.request.StatusRequestDto;
import org.hrms.dto.response.CreateExpenseResponseDto;
import org.hrms.repository.entity.Expense;
import org.hrms.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hrms.constant.EndPoints.*;

@RestController
@RequestMapping(EXPENSE)
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService service;

    @GetMapping("/gatewaytest")
    public ResponseEntity<String> testinggateway() {
        return ResponseEntity.ok("Calisiyor");
    }

    @PostMapping(SAVE)
    public ResponseEntity<CreateExpenseResponseDto> save(@RequestBody CreateExpenseRequestDto dto){
        return ResponseEntity.ok(service.createExpense(dto));
    }

    @PutMapping(APPROVESTATUS)
    public ResponseEntity<Boolean> approveStatus(@RequestBody StatusRequestDto dto) {
        return ResponseEntity.ok(service.approveStatus(dto));
    }

    @PutMapping(DENYSTATUS)
    public ResponseEntity<Boolean> denyStatus(@RequestBody StatusRequestDto dto) {
        return ResponseEntity.ok(service.denyStatus(dto));
    }
    @GetMapping(FINDEXPENSEBYCOMPANY)
    public ResponseEntity<List<Expense>> findExpenseByCompany(@RequestParam String companyName){
        return ResponseEntity.ok(service.findExpenseByCompany(companyName));
    }

}
