package org.hrms.controller;

import lombok.RequiredArgsConstructor;

import org.hrms.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hrms.constant.EndPoints.*;

@RestController
@RequestMapping(EXPENSE)
@RequiredArgsConstructor
public class CommentController {

    private final ExpenseService service;

    @GetMapping("/gatewaytest")
    public ResponseEntity<String> testinggateway() {
        return ResponseEntity.ok("Calisiyor");
    }

}
