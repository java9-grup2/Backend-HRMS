package org.hrms.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.ActivateStatusModel;
import org.hrms.rabbitmq.model.ApproveManagerMailModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApproveManagerMailProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.userExchange}")
    private String exchangeUser;

    @Value("${rabbitmq.approve-manager-mail-bindingKey}")
    private String approveManagerMailBindingKey;

    public void sendApproveManagerMail(ApproveManagerMailModel model){
        rabbitTemplate.convertAndSend(exchangeUser,approveManagerMailBindingKey,model);
    }
}
