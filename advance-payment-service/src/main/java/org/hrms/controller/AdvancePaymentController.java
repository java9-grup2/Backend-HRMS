package org.hrms.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hrms.constant.EndPoints;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.hrms.constant.EndPoints.ADVANCEPAYMENT;

@RestController
@RequestMapping(ADVANCEPAYMENT)
@RequiredArgsConstructor
public class AdvancePaymentController {

    @GetMapping("apitest")
    public ResponseEntity<String> mesaj() {
        return ResponseEntity.ok("api calisiyor");
    }
}
