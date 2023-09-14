package org.hrms.config.rabbitmq;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.authExchange}")
    private String authExchange;

    @Value("${rabbitmq.register-visitor-queue}")
    private String registerVisitorQueue;

    @Value("${rabbitmq.register-visitor-bindingKey}")
    private String registerVisitorBindingKey;


    @Value("${rabbitmq.register-manager-queue}")
    private String registerManagerQueue;

    @Value("${rabbitmq.register-manager-bindingKey}")
    private String registerManagerBindingKey;



    @Value("${rabbitmq.activation-mail-queue}")
    private String activationMailQueue;

    @Value("${rabbitmq.activation-mail-bindingKey}")
    private String activationMailBindingKey;


    @Value("${rabbitmq.register-employee-mail-queue}")
    private String registerEmployeeMailQueue;

    @Value("${rabbitmq.register-employee-bindingKey}")
    private String registerEmployeeBindingKey;

    @Bean
    DirectExchange exchangeAuth() {
        return new DirectExchange(authExchange);
    }



    @Bean
    Queue registerEmployeeMailQueue(){
        return new Queue(registerEmployeeMailQueue);
    }

    @Bean
    public Binding registerEmployeeMailBinding(final DirectExchange exchangeAuth, final Queue registerEmployeeMailQueue) {
        return BindingBuilder.bind(registerEmployeeMailQueue).to(exchangeAuth).with(registerEmployeeBindingKey);
    }

    @Bean
    Queue activationMailQueue() {
        return new Queue(activationMailQueue);
    }

    @Bean
    public Binding activationMailBinding(final DirectExchange exchangeAuth, final Queue activationMailQueue) {
        return BindingBuilder.bind(activationMailQueue).to(exchangeAuth).with(activationMailBindingKey);
    }


    @Bean
    Queue registerManagerQueue() {
        return new Queue(registerManagerQueue);
    }

    @Bean
    public Binding registerManagerBinding(final DirectExchange exchangeAuth, final Queue registerManagerQueue) {
        return BindingBuilder.bind(registerManagerQueue).to(exchangeAuth).with(registerManagerBindingKey);
    }

    @Bean
    Queue registerVisitorQueue() {
        return new Queue(registerVisitorQueue);
    }


    @Bean
    public Binding registerVisitorBinding(final DirectExchange exchangeAuth, final Queue registerVisitorQueue) {
        return BindingBuilder.bind(registerVisitorQueue).to(exchangeAuth).with(registerVisitorBindingKey);
    }
}
