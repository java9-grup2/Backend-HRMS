package org.hrms.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserByAuthIdProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.userExchange}")
    private String exchangeUser;

    @Value("${rabbitmq.delete-user-by-authid-bindingKey}")
    private String deleteUserByAuthidBindingKey;

    public void deleteUserByAuthid(Long authid) {
        rabbitTemplate.convertAndSend(exchangeUser,deleteUserByAuthidBindingKey,authid);
    }
}
