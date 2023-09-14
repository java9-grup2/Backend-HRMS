package org.hrms.rabbitmq.producer;

import lombok.*;
import org.hrms.rabbitmq.model.ActivationMailModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivationMailProducer {

    private final RabbitTemplate rabbitTemplate;


    @Value("${rabbitmq.authExchange}")
    private String authExchange;

    @Value("${rabbitmq.activation-mail-bindingKey}")
    private String activationMailBindingKey;

    public void sendActivationMail(ActivationMailModel model) {
        rabbitTemplate.convertAndSend(authExchange,activationMailBindingKey,model);
    }

}
