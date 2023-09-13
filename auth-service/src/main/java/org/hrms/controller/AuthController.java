package org.hrms.controller;

import lombok.RequiredArgsConstructor;
import org.hrms.dto.request.ActivationRequestDto;
import org.hrms.dto.request.LoginRequestDto;
import org.hrms.dto.request.RegisterManagerRequestDto;
import org.hrms.dto.request.RegisterVisitorRequestDto;
import org.hrms.dto.response.TokenResponseDto;
import org.hrms.dto.response.MessageResponseDto;
import org.hrms.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @PostMapping(LOGIN)
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto dto) {
        return ResponseEntity.ok(service.login(dto));
    }

    @PostMapping(ACTIVATION)
    public ResponseEntity<MessageResponseDto> activateStatus(@Valid @RequestBody ActivationRequestDto dto) {
        return ResponseEntity.ok(service.activateStatus(dto));
    }
}
