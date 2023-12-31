package org.hrms.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.RegisterEmployeeMailModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterEmployeeMailProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.authExchange}")
    private String authExchange;

    @Value("${rabbitmq.register-employee-bindingKey}")
    private String registerEmployeeBindingKey;

    public void sendEmployeeDetails(RegisterEmployeeMailModel model) {
        rabbitTemplate.convertAndSend(authExchange,registerEmployeeBindingKey,model);
    }
}
