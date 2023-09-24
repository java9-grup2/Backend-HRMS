package org.hrms.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.ForgotPasswordMailModel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class ForgotPasswordMailProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.authExchange}")
    private String authExchange;

    @Value("${rabbitmq.forgot-password-mail-bindingKey}")
    private String forgotPasswordMailBindingKey;

    public void forgotPasswordMailSender(ForgotPasswordMailModel model) {
        rabbitTemplate.convertAndSend(authExchange,forgotPasswordMailBindingKey,model);
    }
}
