package org.hrms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ShiftsAndBreaksServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShiftsAndBreaksServiceApplication.class);
    }
}