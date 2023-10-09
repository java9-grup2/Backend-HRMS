package org.hrms.controller;

import lombok.RequiredArgsConstructor;
import org.hrms.dto.request.AdvanceStatusRequestDto;
import org.hrms.dto.request.CreateAdvancePaymentRequestDto;
import org.hrms.dto.response.CreateAdvancePaymentResponseDto;
import org.hrms.repository.entity.AdvancePayment;
import org.hrms.service.AdvancePaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.hrms.constant.EndPoints.*;

@RestController
@RequestMapping(ADVANCEPAYMENT)
@RequiredArgsConstructor
public class AdvancePaymentController {

    private final AdvancePaymentService service;


    @GetMapping("apitest")
    public ResponseEntity<String> mesaj() {
        return ResponseEntity.ok("api calisiyor");
    }

    @PostMapping(SAVE)
    public ResponseEntity<CreateAdvancePaymentResponseDto> createAdvancePayment(@Valid @RequestBody CreateAdvancePaymentRequestDto dto) {
        return ResponseEntity.ok(service.createAdvancePayment(dto));
    }

    @PutMapping(APPROVEADVANCEREQUEST)
    public ResponseEntity<Boolean> approveAdvancePaymentRequest(@RequestBody AdvanceStatusRequestDto dto) {
        return ResponseEntity.ok(service.approveAdvancePaymentRequest(dto));
    }

    @PutMapping(DENYADVANCEREQUEST)
    public ResponseEntity<Boolean> denyAdvancePaymentRequest(@RequestBody AdvanceStatusRequestDto dto) {
        return ResponseEntity.ok(service.denyAdvancePaymentRequest(dto));
    }

    @GetMapping(LISTADVANCEPAYMENTREQEUSTS)
    public ResponseEntity<List<AdvancePayment>> listAdvancePaymentRequests(@RequestParam String companyName) {
        return ResponseEntity.ok(service.listAdvancePaymentRequests(companyName));
    }

}
