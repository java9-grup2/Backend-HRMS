package org.hrms.controller;

import lombok.RequiredArgsConstructor;
import org.hrms.dto.request.*;
//import org.hrms.dto.request.ListWorkersRequestDto;
import org.hrms.dto.response.ApproveCommentOfEmployeeResponseDto;
import org.hrms.rabbitmq.model.*;
import org.hrms.repository.entity.User;
import org.hrms.repository.enums.EUserType;
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

    @PostMapping(ISCOMMENTMATCHES)
    public ResponseEntity<Boolean> isCommentDetailsValid(@RequestBody IsCommentMatchesRequestDto dto) {
        return ResponseEntity.ok(service.isCommentDetailsValid(dto));
    }

    @PostMapping(APPROVECOMMENTOFEMPLOYEE)
    public ResponseEntity<ApproveCommentOfEmployeeResponseDto> approveCommentOfEmployee(@RequestBody ApproveCommentOfEmployeeRequestDto dto) {
        return ResponseEntity.ok(service.approveCommentOfEmployee(dto));
    }

    @PostMapping(DENYCOMMENT)
    public ResponseEntity<Boolean> denyCommentOfEmployee(@RequestBody DenyCommentOfEmployeeRequestDto dto) {
        return ResponseEntity.ok(service.denyCommentOfEmployee(dto));
    }

    @GetMapping(LISTPENDINGMANAGERAPPROVAL)
    public ResponseEntity<List<User>> listPendingManagerApproval() {
        return ResponseEntity.ok(service.listPendingManagerApproval());
    }

    @PostMapping(APPROVEMANAGER)
    public ResponseEntity<Boolean> approveManager(@RequestBody ApproveManagerRequestDto dto) {

        return ResponseEntity.ok(service.approveManagerUser(dto));
    }

    @PostMapping(DENYMANAGER)
    public ResponseEntity<Boolean> denyRegisterManager(@RequestBody DenyManagerRequestDto dto) {
        return ResponseEntity.ok(service.denyRegisterManager(dto));
    }

    @GetMapping(EMPLOYEEINFO)
    public ResponseEntity<User> showEmployeeInfo(@RequestParam String token) {
        return ResponseEntity.ok(service.showPersonelInfo(token));
    }

    @GetMapping("getUserType")
    public ResponseEntity<EUserType> getUserType(@RequestParam Long authid){
        return ResponseEntity.ok(service.getUserType(authid));
    }

    @GetMapping("getUsername")
    public ResponseEntity<String> getUsername(@RequestParam Long authid){
        return ResponseEntity.ok(service.getUsername(authid));
    }

    @PostMapping(ISDAYOFFREQUESTVALID)
    public ResponseEntity<Boolean> isDayOffRequestValid(@RequestBody AuthIdAndCompanyNameCheckerRequestDto dto) {
        return ResponseEntity.ok(service.isRequestValid(dto));
    }

    @PostMapping(ISEXPENSEREQUESTVALID)
    public ResponseEntity<Boolean> isExpenseRequestValid(@RequestBody AuthIdAndCompanyNameCheckerRequestDto dto) {
        return ResponseEntity.ok(service.isRequestValid(dto));
    }


}


