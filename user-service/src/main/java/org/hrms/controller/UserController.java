package org.hrms.controller;

import lombok.RequiredArgsConstructor;
import org.hrms.dto.request.RegisterVisitorRequestDto;
import org.hrms.repository.entity.User;
import org.hrms.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import static org.hrms.constant.EndPoints.*;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping(SAVE)
    public ResponseEntity<User> registerVisitor(@Valid @RequestBody RegisterVisitorRequestDto dto) {
        return ResponseEntity.ok(service.saveUser(dto));
    }

}


