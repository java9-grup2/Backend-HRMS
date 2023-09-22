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

    @Value("${rabbitmq.delete-user-by-authid-queue}")
    private String deleteUserByAuthidQueue;

    @Value("${rabbitmq.delete-user-by-authid-bindingKey}")
    private String deleteUserByAuthidBindingKey;

    @Value("${rabbitmq.delete-auth-by-id-queue}")
    private String deleteAuthByIdQueue;

    @Value("${rabbitmq.approve-manager-mail-queue}")
    private String approveManagerMailQueue;

    @Value("${rabbitmq.approve-manager-mail-bindingKey}")
    private String approveManagerMailBindingKey;

    @Value("${rabbitmq.create-admin-user-queue}")
    private String createAdminUserQueue;

    @Value("${rabbitmq.activate-manager-status-bindingKey}")
    private String activateManagerStatusBindingKey;

    @Value("${rabbitmq.activate-manager-status-queue}")
    private String activateManagerStatusQueue;


    @Bean
    DirectExchange exchangeUser() {
        return new DirectExchange(exchangeUser);
    }


    @Bean
    Queue activateManagerStatusQueue() {
        return new Queue(activateManagerStatusQueue);
    }

    @Bean
    public Binding activateManagerStatusBinding(final DirectExchange exchangeUser,final Queue activateManagerStatusQueue) {
        return BindingBuilder.bind(activateManagerStatusQueue).to(exchangeUser).with(activateManagerStatusBindingKey);
    }

    @Bean
    Queue createAdminUserQueue() {
        return new Queue(createAdminUserQueue);
    }


    @Bean
    Queue approveManagerMailQueue() {
        return new Queue(approveManagerMailQueue);
    }

    @Bean
    public Binding approveManagerBinding(final DirectExchange exchangeUser, final Queue approveManagerMailQueue ) {
        return BindingBuilder.bind(approveManagerMailQueue).to(exchangeUser).with(approveManagerMailBindingKey);
    }

    @Bean
    Queue deleteAuthByIdQueue() {
        return new Queue(deleteAuthByIdQueue);
    }
    @Bean
    Queue deleteUserByAuthidQueue() {
        return new Queue(deleteUserByAuthidQueue);
    }

    @Bean
    public Binding deleteUserByAuthidBinding(final DirectExchange exchangeUser, final Queue deleteUserByAuthidQueue) {
        return BindingBuilder.bind(deleteUserByAuthidQueue).to(exchangeUser).with(deleteUserByAuthidBindingKey);
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
