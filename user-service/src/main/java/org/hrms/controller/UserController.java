package org.hrms.controller;

import lombok.RequiredArgsConstructor;
import org.hrms.dto.request.RegisterVisitorRequestDto;
import org.hrms.rabbitmq.model.RegisterManagerModel;
import org.hrms.rabbitmq.model.RegisterVisitorModel;
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

    @PostMapping(SAVEVISITOR)
    public ResponseEntity<User> registerVisitor(@RequestBody RegisterVisitorModel model) {
        return ResponseEntity.ok(service.saveVisitorUser(model));
    }


    @PostMapping(SAVEMANAGER)
    public ResponseEntity<User> registerManager(@RequestBody RegisterManagerModel model) {
        return ResponseEntity.ok(service.saveManagerUser(model));
    }
}


