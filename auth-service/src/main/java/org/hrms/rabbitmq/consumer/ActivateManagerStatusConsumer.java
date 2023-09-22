package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.service.AuthService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivateManagerStatusConsumer {

    private final AuthService authService;

    @RabbitListener(queues = "${rabbitmq.activate-manager-status-queue}")
    public void activateManagerStatus(Long authid) {
        authService.activateManagerStatus(authid);
    }
}
