package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.ApproveManagerMailModel;
import org.hrms.service.MailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@RequiredArgsConstructor
public class ApproveManagerMailConsumer {

    private final MailService mailService;

    @RabbitListener(queues = "${rabbitmq.approve-manager-mail-queue}")
    public void sendApproveMailToManager(ApproveManagerMailModel model) throws MessagingException {
        mailService.sendApproveManagerMail(model);
    }
}
