package org.hrms.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.CreateCompanyPackageModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCompanyPackageProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.authExchange}")
    private String authExchange;

    @Value("${rabbitmq.create-company-package-bindingKey}")
    private String createCompanyPackageBindingKey;

    public void createCompanyPackage(CreateCompanyPackageModel model) {
        rabbitTemplate.convertAndSend(authExchange, createCompanyPackageBindingKey, model);
    }

}
