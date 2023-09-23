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

    @Value("${rabbitmq.save-employee-queue}")
    private String saveEmployeeQueue;

    @Value("${rabbitmq.save-employee-bindingKey}")
    private String saveEmployeeBindingKey;

    @Value("${rabbitmq.activate-status-queue}")
    private String activateStatusQueue;

    @Value("${rabbitmq.activate-status-bindingKey}")
    private String activateStatusBindingKey;

    @Value("${rabbitmq.update-user-queue}")
    private String updateUserQueue;

    @Value("${rabbitmq.delete-user-by-authid-queue}")
    private String deleteUserByAuthidQueue;


    @Value("${rabbitmq.create-company-queue}")
    private String createCompanyQueue;

    @Value("${rabbitmq.create-company-bindingKey}")
    private String createCompanyBindingKey;

    @Value("${rabbitmq.create-admin-user-queue}")
    private String createAdminUserQueue;

    @Value("${rabbitmq.create-admin-user-bindingKey}")
    private String createAdminUserBindingKey;

    @Value("${rabbitmq.activate-manager-status-queue}")
    private String activateManagerStatusQueue;

    @Value("${rabbitmq.delete-auth-contains-companyName-queue}")
    private String deleteAuthContainsCompanyNameQueue;

    @Bean
    DirectExchange exchangeAuth() {
        return new DirectExchange(authExchange);
    }


    @Bean
    Queue deleteAuthContainsCompanyNameQueue() {
        return new Queue(deleteAuthContainsCompanyNameQueue);
    }

    @Bean
    Queue activateManagerStatusQueue() {
        return new Queue(activateManagerStatusQueue);
    }
    @Bean
    Queue createAdminUserQueue() {
        return new Queue(createAdminUserQueue);
    }

    @Bean
    public Binding createAdminUserBinding(final DirectExchange exchangeAuth, final Queue createAdminUserQueue) {
        return BindingBuilder.bind(createAdminUserQueue).to(exchangeAuth).with(createAdminUserBindingKey);
    }

    @Bean
    Queue createCompanyQueue() {
        return new Queue(createCompanyQueue);
    }

    @Bean
    public Binding createCompanyBinding(final DirectExchange exchangeAuth,final Queue createCompanyQueue) {
        return BindingBuilder.bind(createCompanyQueue).to(exchangeAuth).with(createCompanyBindingKey);
    }

    @Bean
    Queue deleteUserByAuthidQueue() {
        return new Queue(deleteUserByAuthidQueue);
    }

    @Bean
    Queue updateUserQueue() {
        return new Queue(updateUserQueue);
    }

    @Bean
    Queue activateStatusQueue() {
        return new Queue(activateStatusQueue);
    }

    @Bean
    public Binding activateStatusBinding(final DirectExchange exchangeAuth, final Queue activateStatusQueue) {
        return BindingBuilder.bind(activateStatusQueue).to(exchangeAuth).with(activateStatusBindingKey);
    }

    @Bean
    Queue saveEmployeeQueue() {
        return new Queue(saveEmployeeQueue);
    }

    @Bean
    public Binding saveEmployeeBinding(final DirectExchange exchangeAuth, final Queue saveEmployeeQueue) {
        return BindingBuilder.bind(saveEmployeeQueue).to(exchangeAuth).with(saveEmployeeBindingKey);
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
