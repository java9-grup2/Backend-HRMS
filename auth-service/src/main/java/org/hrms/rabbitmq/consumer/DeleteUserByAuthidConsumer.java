package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.service.AuthService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserByAuthidConsumer {

    private final AuthService authService;

    @RabbitListener(queues = "${rabbitmq.delete-user-by-authid-queue}")
    public void deleteUserByAuthid(Long authid) {
        authService.deleteAuthById(authid);
    }
}
