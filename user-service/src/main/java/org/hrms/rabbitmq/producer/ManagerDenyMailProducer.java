package org.hrms.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.ManagerDenyMailModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerDenyMailProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.userExchange}")
    private String exchangeUser;
    @Value("${rabbitmq.manager-deny-mail-bindingKey}")
    private String managerDenyMailBindingKey;

    public void sendDenyMailToManager(ManagerDenyMailModel model) {
        rabbitTemplate.convertAndSend(exchangeUser, managerDenyMailBindingKey, model);
    }
}
