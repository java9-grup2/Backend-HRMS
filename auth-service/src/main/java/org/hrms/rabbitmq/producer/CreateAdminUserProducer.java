package org.hrms.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.CreateAdminUserModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAdminUserProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.create-admin-user-bindingKey}")
    private String createAdminUserBindingKey;


    @Value("${rabbitmq.authExchange}")
    private String authExchange;

    public void createAdminUser(CreateAdminUserModel model) {
        rabbitTemplate.convertAndSend(authExchange,createAdminUserBindingKey,model);
    }
}
