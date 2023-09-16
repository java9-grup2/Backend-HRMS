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


    @Value("${rabbitmq.userExchange}")
    private String exchangeUser;

    @Value("${rabbitmq.register-visitor-queue}")
    private String registerVisitorQueue;

    @Value("${rabbitmq.register-manager-queue}")
    private String registerManagerQueue;

    @Value("${rabbitmq.save-employee-queue}")
    private String saveEmployeeQueue;

    @Value("${rabbitmq.activate-status-queue}")
    private String activateStatusQueue;


    @Value("${rabbitmq.update-user-queue}")
    private String updateUserQueue;

    @Value("${rabbitmq.update-user-bindingKey}")
    private String updateUserBindingKey;

    @Bean
    DirectExchange exchangeUser() {
        return new DirectExchange(exchangeUser);
    }

    @Bean
    Queue updateUserQueue() {
        return new Queue(updateUserQueue);
    }

    @Bean
    public Binding updateUserBinding(final DirectExchange exchangeUser, final Queue updateUserQueue) {
        return BindingBuilder.bind(updateUserQueue).to(exchangeUser).with(updateUserBindingKey);
    }


    @Bean
    Queue activateStatusQueue() {
        return new Queue(activateStatusQueue);
    }

    @Bean
    Queue saveEmployeeQueue() {
        return new Queue(saveEmployeeQueue);
    }

    @Bean
    Queue registerManagerQueue() {
        return new Queue(registerManagerQueue);
    }

    @Bean
    Queue registerVisitorQueue() {
        return new Queue(registerVisitorQueue);
    }

}
