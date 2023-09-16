package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.UpdateUserModel;
import org.hrms.service.AuthService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserConsumer {


    private final AuthService authService;

    @RabbitListener(queues = "${rabbitmq.update-user-queue}")
    public void updateAuth(UpdateUserModel model) {
        authService.updateAuth(model);
    }
}
