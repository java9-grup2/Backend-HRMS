package org.hrms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AdvancePaymentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdvancePaymentServiceApplication.class);
    }
}