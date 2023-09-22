package org.hrms.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivateManagerStatusProducer {

    private final RabbitTemplate rabbitTemplate;


    @Value("${rabbitmq.userExchange}")
    private String exchangeUser;

    @Value("${rabbitmq.activate-manager-status-bindingKey}")
    private String activateManagerStatusBindingKey;


    public void activateManagerStatus(Long authid) {
        rabbitTemplate.convertAndSend(exchangeUser,activateManagerStatusBindingKey,authid);
    }
}
