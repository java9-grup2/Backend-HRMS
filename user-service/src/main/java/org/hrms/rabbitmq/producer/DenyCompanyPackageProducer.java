package org.hrms.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.DenyCompanyPackageModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DenyCompanyPackageProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.deny-company-package-bindingKey}")
    private String denyCompanyPackageBindingKey;

    @Value("${rabbitmq.userExchange}")
    private String exchangeUser;

    public void denyCompanyPackage(DenyCompanyPackageModel model) {
        rabbitTemplate.convertAndSend(exchangeUser, denyCompanyPackageBindingKey, model);
    }
}
