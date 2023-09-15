package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.RegisterManagerModel;
import org.hrms.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RegisterManagerConsumer {

    private final UserService userService;


    @RabbitListener(queues = "${rabbitmq.register-manager-queue}")
    public void saveRegisterManagerModel(RegisterManagerModel model) {
        userService.saveManagerUser(model);
    }
}
