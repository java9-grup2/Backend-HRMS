package org.hrms.controller;

import lombok.RequiredArgsConstructor;
import org.hrms.dto.request.*;
import org.hrms.dto.response.MessageResponseDto;
import org.hrms.dto.response.TokenResponseDto;
import org.hrms.rabbitmq.model.DeleteAuthContainsCompanyNameModel;
import org.hrms.rabbitmq.model.UpdateAuthCompanyNameDetailsModel;
import org.hrms.rabbitmq.model.UpdateUserModel;
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

    @PutMapping(UPDATE)
    public ResponseEntity<Boolean> updateAuth(@RequestBody UpdateUserModel model) {
        return ResponseEntity.ok(service.updateAuth(model));
    }

    @DeleteMapping(DELETEBYID)
    public ResponseEntity<Boolean> deleteAuthById(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteAuthById(id));
    }

    @DeleteMapping(DELETEBYCOMPANYNAME)
    public ResponseEntity<Boolean> deleteByCompanyName(@RequestBody DeleteAuthContainsCompanyNameModel model) {
        return ResponseEntity.ok(service.deleteByCompanyName(model));
    }

    @PutMapping(UPDATECOMPANYDETAILS)
    public ResponseEntity<Boolean> updateCompanyDetails(@RequestBody UpdateAuthCompanyNameDetailsModel model) {
        return ResponseEntity.ok(service.updateCompanyDetails(model));
    }

    @PostMapping(FORGOTPASSWORD)
    public ResponseEntity<Boolean> forgotPassword(@RequestParam String email) {
        return ResponseEntity.ok(service.forgotPassword(email));
    }
}
