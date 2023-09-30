package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.ActivateCompanyStatusModel;
import org.hrms.service.CompanyService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivateCompanyStatusConsumer {

    private final CompanyService service;

    @RabbitListener(queues = "${rabbitmq.activate-company-status-queue}")
    public void activateStatusCompany(ActivateCompanyStatusModel model) {
        service.activateCompanyStatus(model);
    }
}
