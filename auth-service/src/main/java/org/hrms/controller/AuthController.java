package org.hrms.controller;

import lombok.RequiredArgsConstructor;
import org.hrms.dto.request.*;
import org.hrms.dto.response.TokenResponseDto;
import org.hrms.dto.response.MessageResponseDto;
import org.hrms.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.hrms.constant.EndPoints.*;

@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping(REGISTERVISITOR)
    public ResponseEntity<TokenResponseDto> registerVisitor(@Valid @RequestBody RegisterVisitorRequestDto dto) {
        return ResponseEntity.ok(service.registerVisitor(dto));
    }

    @PostMapping(REGISTERMANAGER)
    public ResponseEntity<TokenResponseDto> registerManager(@Valid @RequestBody RegisterManagerRequestDto dto) {
        return ResponseEntity.ok(service.registerManager(dto));
    }

    @PostMapping(REGISTEREMPLOYEE)
    public ResponseEntity<Boolean> registerEmployee(@Valid @RequestBody RegisterEmployeeRequestDto dto) {
        return ResponseEntity.ok(service.registerEmployee(dto));
    }

    @PostMapping(LOGIN)
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto dto) {
        return ResponseEntity.ok(service.login(dto));
    }

    @GetMapping(ACTIVATION)
    public ResponseEntity<MessageResponseDto> activateStatus(String token) {
        return ResponseEntity.ok(service.activateStatus(token));
    }

    @GetMapping("message")
    public ResponseEntity<String> activateStatus( ) {
        return ResponseEntity.ok("sa");
    }
}
