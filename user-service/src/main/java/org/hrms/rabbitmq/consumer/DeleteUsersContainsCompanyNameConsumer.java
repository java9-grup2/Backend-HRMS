package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.DeleteUsersContainsCompanyNameModel;
import org.hrms.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUsersContainsCompanyNameConsumer {

    private final UserService userService;

    @RabbitListener(queues = "${rabbitmq.delete-users-contains-companyName-queue}")
    public void deleteUsersContainsCompanyName(DeleteUsersContainsCompanyNameModel model) {
        userService.deleteByCompanyName(model);
    }
}
