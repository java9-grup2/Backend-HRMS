package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.RegisterVisitorModel;
import org.hrms.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterVisitorConsumer {


    private final UserService userService;

    @RabbitListener(queues = "${rabbitmq.register-visitor-queue}")
    public void saveRegisterVisitorModel(RegisterVisitorModel model) throws MessagingException {
        userService.saveVisitorUser(model);
    }
}
