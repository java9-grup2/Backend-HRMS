package org.hrms.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.UpdateAuthCompanyNameDetailsModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateAuthCompanyNameDetailsProducer {

    private final RabbitTemplate rabbitTemplate;


    @Value("${rabbitmq.exchangeCompany}")
    private String exchangeCompany;


    @Value("${rabbitmq.update-auth-companyName-details-bindingKey}")
    private String updateAuthCompanyNameDetailsBindingKey;

    public void updateAuthCompanyNameDetails(UpdateAuthCompanyNameDetailsModel model) {
        rabbitTemplate.convertAndSend(exchangeCompany,updateAuthCompanyNameDetailsBindingKey,model);
    }
}
