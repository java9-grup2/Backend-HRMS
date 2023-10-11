package org.hrms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
public class ShiftsAndBreaksServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShiftsAndBreaksServiceApplication.class);
    }
//    @LoadBalanced
//    @Bean
//    public RestTemplate getRestTemplate(){
//        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
//        clientHttpRequestFactory.setConnectTimeout(500);
//        return new RestTemplate(clientHttpRequestFactory);
//    }
}