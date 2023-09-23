package org.hrms.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.DeleteAuthContainsCompanyNameModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAuthContainsCompanyNameProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.userExchange}")
    private String exchangeUser;

    @Value("${rabbitmq.delete-auth-contains-companyName-bindingKey}")
    private String deleteAuthContainsCompanyNameBindingKey;

    public void deleteAuthContainsCompanyName(DeleteAuthContainsCompanyNameModel model) {
        rabbitTemplate.convertAndSend(exchangeUser, deleteAuthContainsCompanyNameBindingKey, model);
    }
}
