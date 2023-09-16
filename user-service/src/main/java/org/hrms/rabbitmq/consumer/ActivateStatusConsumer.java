package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.ActivateStatusModel;
import org.hrms.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivateStatusConsumer {


    private final UserService userService;


    @RabbitListener(queues = "${rabbitmq.activate-status-queue}")
    public void activateStatus(ActivateStatusModel model) {
        userService.activateStatus(model.getAuthid());
    }

}
