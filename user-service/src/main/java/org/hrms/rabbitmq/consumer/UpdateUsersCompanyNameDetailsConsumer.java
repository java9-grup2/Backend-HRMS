package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.UpdateUsersCompanyNameDetailsModel;
import org.hrms.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUsersCompanyNameDetailsConsumer {

    private final UserService userService;

    @RabbitListener(queues = "${rabbitmq.update-users-companyName-details-queue}")
    public void updateUsersCompanyNameDetails(UpdateUsersCompanyNameDetailsModel model) {
        userService.updateCompanyDetails(model);
    }
}
