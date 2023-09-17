package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAuthByIdConsumer {

    private final UserService userService;

    @RabbitListener(queues = "${rabbitmq.delete-auth-by-id-queue}")
    public void deleteAuthById(Long authid) {
        userService.deleteUserByAuthId(authid);
    }
}
