package org.hrms.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.DeleteUsersContainsCompanyNameModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUsersContainsCompanyNameProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchangeCompany}")
    private String exchangeCompany;

    @Value("${rabbitmq.delete-users-contains-companyName-bindingKey}")
    private String deleteUsersContainsCompanyNameBindingKey;

    public void deleteUsersContainsCompanyName(DeleteUsersContainsCompanyNameModel model) {
        rabbitTemplate.convertAndSend(exchangeCompany,deleteUsersContainsCompanyNameBindingKey,model);
    }
}
