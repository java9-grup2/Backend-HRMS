package org.hrms.Config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.exchangeCompany}")
    private String exchangeCompany;

    @Value("${rabbitmq.delete-auth-by-id-queue}")
    private String deleteAuthByIdQueue;

    @Value("${rabbitmq.delete-auth-by-id-bindingKey}")
    private String deleteAuthByIdBindingKey;

    @Value("${rabbitmq.create-company-queue}")
    private String createCompanyQueue;

    @Value("${rabbitmq.delete-users-contains-companyName-queue}")
    private String deleteUsersContainsCompanyNameQueue;

    @Value("${rabbitmq.delete-users-contains-companyName-bindingKey}")
    private String deleteUsersContainsCompanyNameBindingKey;

    @Value("${rabbitmq.update-users-companyName-details-queue}")
    private String updateUsersCompanyNameDetailsQueue;

    @Value("${rabbitmq.update-users-companyName-details-bindingKey}")
    private String updateUsersCompanyNameDetailsBindingKey;


    @Value("${rabbitmq.update-auth-companyName-details-Queue}")
    private String updateAuthCompanyNameDetailsQueue;

    @Value("${rabbitmq.update-auth-companyName-details-bindingKey}")
    private String updateAuthCompanyNameDetailsBindingKey;


    @Value("${rabbitmq.delete-company-by-register-deny-Queue}")
    private String deleteCompanyByRegisterDenyQueue;

    @Bean
    DirectExchange exchangeCompany() {
        return new DirectExchange(exchangeCompany);
    }


    @Bean
    Queue deleteCompanyByRegisterDenyQueue() {
        return new Queue(deleteCompanyByRegisterDenyQueue);
    }

    @Bean
    Queue updateAuthCompanyNameDetailsQueue() {
        return new Queue(updateAuthCompanyNameDetailsQueue);
    }

    @Bean
    public Binding updateAuthCompanyNameDetailsBinding(final DirectExchange exchangeCompany, final Queue updateAuthCompanyNameDetailsQueue) {
        return BindingBuilder.bind(updateAuthCompanyNameDetailsQueue).to(exchangeCompany).with(updateAuthCompanyNameDetailsBindingKey);
    }


    @Bean
    Queue updateUsersCompanyNameDetailsQueue() {
        return new Queue(updateUsersCompanyNameDetailsQueue);
    }

    @Bean
    public Binding updateUsersCompanyNameDetailsBinding(final DirectExchange exchangeCompany, final Queue updateUsersCompanyNameDetailsQueue) {
        return BindingBuilder.bind(updateUsersCompanyNameDetailsQueue).to(exchangeCompany).with(updateUsersCompanyNameDetailsBindingKey);
    }

    @Bean
    Queue deleteUsersContainsCompanyNameQueue() {
        return new Queue(deleteUsersContainsCompanyNameQueue);
    }

    @Bean
    public Binding deleteUsersByCompanyNameBinding(final DirectExchange exchangeCompany, final Queue deleteUsersContainsCompanyNameQueue) {
        return BindingBuilder.bind(deleteUsersContainsCompanyNameQueue).to(exchangeCompany).with(deleteUsersContainsCompanyNameBindingKey);
    }

    @Bean
    Queue deleteAuthByIdQueue() {
        return new Queue(deleteAuthByIdQueue);
    }

    @Bean
    public Binding deleteAuthByIdBinding(final DirectExchange exchangeCompany,final Queue deleteAuthByIdQueue){
        return BindingBuilder.bind(deleteAuthByIdQueue).to(exchangeCompany).with(deleteAuthByIdBindingKey);
    }


    @Bean
    Queue createCompanyQueue() {
        return new Queue(createCompanyQueue);
    }
}
