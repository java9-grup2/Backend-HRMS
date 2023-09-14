package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.RegisterEmployeeModel;
import org.hrms.service.MailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@RequiredArgsConstructor
public class RegisterEmployeeConsumer {

    private final MailService mailService;


    @RabbitListener(queues = "${rabbitmq.register-employee-mail-queue}")
    public void sendEmployeeRegisterDetails(RegisterEmployeeModel model) throws MessagingException {
        mailService.sendRegisterEmployeeMail(model);
    }
}
