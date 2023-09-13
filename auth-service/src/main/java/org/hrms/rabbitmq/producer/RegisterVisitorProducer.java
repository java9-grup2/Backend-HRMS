package org.hrms.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.RegisterVisitorModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterVisitorProducer {

    @Value("${rabbitmq.authExchange}")
    private String authExchange;

    @Value("${rabbitmq.register-visitor-bindingKey}")
    private String registerVisitorBindingKey;

    private final RabbitTemplate rabbitTemplate;


    public void registerVisitor(RegisterVisitorModel model) {
        rabbitTemplate.convertAndSend(authExchange,registerVisitorBindingKey,model);
    }
}
