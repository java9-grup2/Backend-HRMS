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


    @Bean
    DirectExchange exchangeCompany() {
        return new DirectExchange(exchangeCompany);
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
