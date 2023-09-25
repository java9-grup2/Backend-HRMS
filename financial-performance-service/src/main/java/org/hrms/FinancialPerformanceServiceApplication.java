package org.hrms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FinancialPerformanceServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinancialPerformanceServiceApplication.class);
    }
}