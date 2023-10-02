package org.hrms.controller;

import lombok.RequiredArgsConstructor;

import org.hrms.dto.request.CreateExpenseRequestDto;
import org.hrms.dto.response.CreateExpenseResponseDto;
import org.hrms.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
