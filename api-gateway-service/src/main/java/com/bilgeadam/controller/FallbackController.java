package com.bilgeadam.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/fallback")
public class FallbackController {


    @GetMapping("/authservice")
    public ResponseEntity<String> authServiceFallback(){
        return  ResponseEntity.ok("Auth service suanda hizmet verememektedir!!!");
    }

    @GetMapping("/userservice")
    public ResponseEntity<String> userServiceFallback(){
        return  ResponseEntity.ok("User service suanda hizmet verememektedir!!!");
    }
    @GetMapping("/companyservice")
    public ResponseEntity<String> companyServiceFallback(){
        return  ResponseEntity.ok("Company service suanda hizmet verememektedir!!!");
    }

    @GetMapping("/financialperformance")
    public ResponseEntity<String> financialPerformanceServiceFallback(){
        return  ResponseEntity.ok("Financial performance service suanda hizmet verememektedir!!!");
    }

    @GetMapping("/commentservice")
    public ResponseEntity<String> commentServiceFallback(){
        return  ResponseEntity.ok("Comment service suanda hizmet verememektedir!!!");
    }
    @GetMapping("/permissionservice")
    public ResponseEntity<String> permissionServiceFallback(){
        return  ResponseEntity.ok("Permission service suanda hizmet verememektedir!!!");
    }

    @GetMapping("/upcomingpaymentservice")
    public ResponseEntity<String> upcomingPaymentServiceFallback(){
        return  ResponseEntity.ok("Upcoming Paymnet service suanda hizmet verememektedir!!!");
    }

    @GetMapping("/shiftsandbreaksservice")
    public ResponseEntity<String> shiftsAndBreaksServiceFallback(){
        return  ResponseEntity.ok("Shifts and Breaks service suanda hizmet verememektedir!!!");
    }

    @GetMapping("/expenceservice")
    public ResponseEntity<String> expenseServiceFallback(){
        return  ResponseEntity.ok("Expense service suanda hizmet verememektedir!!!");
    }

    @GetMapping("advancepaymentservice")
    public ResponseEntity<String> advancePaymentServiceFallback() {
        return ResponseEntity.ok("Advance Payment Service suanda hizmet verememektedir!!!");
    }

    @GetMapping("packageservice")
    public ResponseEntity<String> packageServiceFallback() {
        return ResponseEntity.ok("Package Service suanda hizmet verememektedir!!!");
    }
}
