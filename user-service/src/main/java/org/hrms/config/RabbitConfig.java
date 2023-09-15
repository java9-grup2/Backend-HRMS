package org.hrms.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.register-visitor-queue}")
    private String registerVisitorQueue;

    @Value("${rabbitmq.register-manager-queue}")
    private String registerManagerQueue;



    @Bean
    Queue registerManagerQueue() {
        return new Queue(registerManagerQueue);
    }

    @Bean
    Queue registerVisitorQueue() {
        return new Queue(registerVisitorQueue);
    }

}
