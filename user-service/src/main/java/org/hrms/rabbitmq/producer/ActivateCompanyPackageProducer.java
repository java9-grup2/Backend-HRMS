package org.hrms.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.ActivateCompanyPackageModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivateCompanyPackageProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.userExchange}")
    private String exchangeUser;

    @Value("${rabbitmq.activate-company-package-bindingKey}")
    private String activateCompanyPackageBindingKey;

    public void activateCompanyPackage(ActivateCompanyPackageModel model) {
        rabbitTemplate.convertAndSend(exchangeUser, activateCompanyPackageBindingKey, model);
    }
}
