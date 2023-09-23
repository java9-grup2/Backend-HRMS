package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.DeleteAuthContainsCompanyNameModel;
import org.hrms.service.AuthService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAuthContainsCompanyNameConsumer {

    private final AuthService authService;

    @RabbitListener(queues = "${rabbitmq.delete-auth-contains-companyName-queue}")
    public void deleteAuthContainsCompanyName(DeleteAuthContainsCompanyNameModel model) {
        authService.deleteByCompanyName(model);
    }
}
