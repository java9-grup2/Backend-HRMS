package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.CreateAdminUserModel;
import org.hrms.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAdminUserConsumer {

    private final UserService userService;

    @RabbitListener(queues = "${rabbitmq.create-admin-user-queue}")
    public void createAdminUser(CreateAdminUserModel model) {
        userService.createAdminUser(model);
    }
}
