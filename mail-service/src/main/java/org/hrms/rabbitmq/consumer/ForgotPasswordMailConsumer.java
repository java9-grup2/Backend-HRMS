package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.ForgotPasswordMailModel;
import org.hrms.service.MailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@RequiredArgsConstructor
public class ForgotPasswordMailConsumer {

    private final MailService mailService;

    @RabbitListener(queues = "${rabbitmq.forgot-password-mail-queue}")
    public void forgotPasswordMailSender(ForgotPasswordMailModel model) throws MessagingException {
        mailService.sendForgotPasswordMail(model);
    }
}
