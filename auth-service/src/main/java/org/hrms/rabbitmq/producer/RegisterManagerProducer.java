package org.hrms.rabbitmq.producer;

import lombok.*;
import org.hrms.rabbitmq.model.RegisterManagerModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
@RequiredArgsConstructor
public class RegisterManagerProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.authExchange}")
    private String authExchange;

    @Value("${rabbitmq.register-manager-bindingKey}")
    private String registerManagerBindingKey;

    public void registerManager(RegisterManagerModel model) {
        rabbitTemplate.convertAndSend(authExchange,registerManagerBindingKey,model);
    }

}
