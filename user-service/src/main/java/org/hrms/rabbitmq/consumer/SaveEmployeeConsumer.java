package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.SaveEmployeeModel;
import org.hrms.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveEmployeeConsumer {

    private final UserService userService;

    @RabbitListener(queues = "${rabbitmq.save-employee-queue}")
    public void saveEmployee(SaveEmployeeModel model) {
        userService.saveEmployee(model);
    }
}
