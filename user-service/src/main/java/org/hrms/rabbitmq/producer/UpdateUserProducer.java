package org.hrms.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.UpdateUserModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserProducer {

    @Value("${rabbitmq.userExchange}")
    private String exchangeUser;

    @Value("${rabbitmq.update-user-bindingKey}")
    private String updateUserBindingKey;


    private final RabbitTemplate rabbitTemplate;

    public void updateUser(UpdateUserModel model) {
        rabbitTemplate.convertAndSend(exchangeUser,updateUserBindingKey,model);
    }
}
