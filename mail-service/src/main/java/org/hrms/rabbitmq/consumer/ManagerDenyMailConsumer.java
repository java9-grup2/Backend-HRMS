package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.ManagerDenyMailModel;
import org.hrms.service.MailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@RequiredArgsConstructor
public class ManagerDenyMailConsumer {

    private final MailService mailService;

    @RabbitListener(queues = "${rabbitmq.manager-deny-mail-queue}")
    public void sendDenyMailToManager(ManagerDenyMailModel model) throws MessagingException {
        mailService.sendDenyMessageToManager(model);
    }
}
