package org.hrms.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.CreateCompanyModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCompanyProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.authExchange}")
    private String authExchange;

    @Value("${rabbitmq.create-company-bindingKey}")
    private String createCompanyBindingKey;

    public void createCompany(CreateCompanyModel model) {
        rabbitTemplate.convertAndSend(authExchange, createCompanyBindingKey, model);
    }
}
