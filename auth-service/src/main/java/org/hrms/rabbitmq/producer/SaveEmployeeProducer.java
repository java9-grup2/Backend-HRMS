package org.hrms.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.SaveEmployeeModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveEmployeeProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.authExchange}")
    private String authExchange;

    @Value("${rabbitmq.save-employee-bindingKey}")
    private String saveEmployeeBindingKey;

    public void saveEmployee(SaveEmployeeModel model) {
        rabbitTemplate.convertAndSend(authExchange,saveEmployeeBindingKey,model);
    }
}
