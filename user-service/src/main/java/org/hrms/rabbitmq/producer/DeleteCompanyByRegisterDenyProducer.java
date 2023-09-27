package org.hrms.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.DeleteCompanyByRegisterDenyModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCompanyByRegisterDenyProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.userExchange}")
    private String exchangeUser;

    @Value("${rabbitmq.delete-company-by-register-deny-bindingKey}")
    private String deleteCompanyByRegisterDenyBindingKey;

    public void deleteCompanyByName(DeleteCompanyByRegisterDenyModel model) {
        rabbitTemplate.convertAndSend(exchangeUser,deleteCompanyByRegisterDenyBindingKey,model);
    }
}
