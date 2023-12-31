package org.hrms.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.activation-mail-queue}")
    private String activationMailQueue;

    @Value("${rabbitmq.register-employee-mail-queue}")
    private String registerEmployeeMailQueue;

    @Value("${rabbitmq.approve-manager-mail-queue}")
    private String approveManagerMailQueue;


    @Value("${rabbitmq.forgot-password-mail-queue}")
    private String forgotPasswordMailQueue;

    @Value("${rabbitmq.manager-deny-mail-queue}")
    private String managerDenyMailQueue;

    @Bean
    Queue managerDenyMailQueue() {
        return new Queue(managerDenyMailQueue);
    }

    @Bean
    Queue forgotPasswordMailQueue() {
        return new Queue(forgotPasswordMailQueue);
    }

    @Bean
    Queue approveManagerMailQueue() {
        return new Queue(approveManagerMailQueue);
    }

    @Bean
    Queue activationMailQueue() {
        return new Queue(activationMailQueue);
    }

    @Bean
    Queue registerEmployeeMailQueue(){
        return new Queue(registerEmployeeMailQueue);
    }

}
