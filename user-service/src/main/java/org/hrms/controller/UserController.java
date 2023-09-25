package org.hrms.controller;

import lombok.RequiredArgsConstructor;
import org.hrms.dto.request.ApproveManagerRequestDto;
//import org.hrms.dto.request.ListWorkersRequestDto;
import org.hrms.dto.request.ListWorkersRequestDto;
import org.hrms.dto.request.RegisterVisitorRequestDto;
import org.hrms.dto.request.UpdateRequestDto;
import org.hrms.rabbitmq.model.*;
import org.hrms.repository.entity.User;
import org.hrms.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

import static org.hrms.constant.EndPoints.*;
import static org.hrms.constant.EndPoints.LISTWORKERS;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping(APPROVEMANAGER)
    public ResponseEntity<Boolean> approveManager(@RequestBody ApproveManagerRequestDto dto) {

        return ResponseEntity.ok(service.approveManagerUser(dto));
    }

    @PostMapping(SAVEVISITOR)
    public ResponseEntity<User> registerVisitor(@RequestBody RegisterVisitorModel model) {
        return ResponseEntity.ok(service.saveVisitorUser(model));
    }

    @PostMapping(SAVEMANAGER)
    public ResponseEntity<User> registerManager(@RequestBody RegisterManagerModel model) {
        return ResponseEntity.ok(service.saveManagerUser(model));
    }

    @PostMapping(SAVEEMPLOYEE)
    public ResponseEntity<Boolean> registerEmployee(@RequestBody SaveEmployeeModel model) {
        return ResponseEntity.ok(service.saveEmployee(model));
    }

    @PostMapping(ACTIVATESTATUS)
    public ResponseEntity<Boolean> activateStatus(@RequestBody Long authid) {
        return ResponseEntity.ok(service.activateStatus(authid));
    }

    @PutMapping(UPDATE)
    public ResponseEntity<Boolean> updateUser(@RequestBody UpdateRequestDto dto) {
        return ResponseEntity.ok(service.updateUser(dto));
    }

    @DeleteMapping(DELETEBYAUTHID)
    public ResponseEntity<Boolean> deleteUserByAuthId(@PathVariable Long authid) {
        return ResponseEntity.ok(service.deleteUserByAuthId(authid));
    }

    @DeleteMapping(DELETEBYCOMPANYNAME)
    public ResponseEntity<Boolean> deleteByCompanyName(@RequestBody DeleteUsersContainsCompanyNameModel model) {
        return ResponseEntity.ok(service.deleteByCompanyName(model));
    }

    @PutMapping(UPDATECOMPANYDETAILS)
    public ResponseEntity<Boolean> updateCompanyDetails(@RequestBody UpdateUsersCompanyNameDetailsModel model) {
        return ResponseEntity.ok(service.updateCompanyDetails(model));
    }


    @GetMapping(LISTWORKERS)
    public ResponseEntity<List<User>> listWorkersAsManager(ListWorkersRequestDto dto) {
        return ResponseEntity.ok(service.listWorkersAsManager(dto));
    }



}


