package org.hrms.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.ActivateStatusModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivateStatusProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.activate-status-bindingKey}")
    private String activateStatusBindingKey;

    @Value("${rabbitmq.authExchange}")
    private String authExchange;

    public void activateStatus(ActivateStatusModel model) {
        rabbitTemplate.convertAndSend(authExchange, activateStatusBindingKey, model);
    }
}
