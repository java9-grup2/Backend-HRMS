package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.ActivationMailModel;
import org.hrms.service.MailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@RequiredArgsConstructor
public class ActivationMailConsumer {


    private final MailService mailService;

    @RabbitListener(queues = "${rabbitmq.activation-mail-queue}")
    public void sendActivationMail(ActivationMailModel model) throws MessagingException {
        mailService.sendActivationMail(model);
    }
}
