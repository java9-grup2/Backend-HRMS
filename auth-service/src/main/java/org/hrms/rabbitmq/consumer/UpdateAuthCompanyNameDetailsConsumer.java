package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.UpdateAuthCompanyNameDetailsModel;
import org.hrms.service.AuthService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateAuthCompanyNameDetailsConsumer {
    private final AuthService authService;


    @RabbitListener(queues = "${rabbitmq.update-auth-companyName-details-Queue}")
    public void updateAuthCompanyNameDetails(UpdateAuthCompanyNameDetailsModel model) {
        authService.updateCompanyDetails(model);
    }
}
