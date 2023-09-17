package org.hrms.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAuthByIdProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchangeCompany}")
    private String exchangeCompany;

    @Value("${rabbitmq.delete-auth-by-id-bindingKey}")
    private String deleteAuthByIdBindingKey;


    public void deleteAuthById(Long authId) {
        rabbitTemplate.convertAndSend(exchangeCompany, deleteAuthByIdBindingKey, authId);
    }
}