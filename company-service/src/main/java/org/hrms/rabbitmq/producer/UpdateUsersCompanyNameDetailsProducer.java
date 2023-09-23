package org.hrms.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.UpdateUsersCompanyNameDetailsModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUsersCompanyNameDetailsProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchangeCompany}")
    private String exchangeCompany;

    @Value("${rabbitmq.update-users-companyName-details-bindingKey}")
    private String updateUsersCompanyNameDetailsBindingKey;

    public void updateUsersCompanyNameDetails(UpdateUsersCompanyNameDetailsModel model) {
        rabbitTemplate.convertAndSend(exchangeCompany, updateUsersCompanyNameDetailsBindingKey, model);
    }
}
