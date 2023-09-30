package org.hrms.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.ActivateCompanyStatusModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivateCompanyStatusProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.activate-company-status-bindingKey}")
    private String activateCompanyStatusBindingKey;

    @Value("${rabbitmq.userExchange}")
    private String exchangeUser;

    public void activateCompanyStatus(ActivateCompanyStatusModel model) {
        rabbitTemplate.convertAndSend(exchangeUser,activateCompanyStatusBindingKey,model);
    }
}
