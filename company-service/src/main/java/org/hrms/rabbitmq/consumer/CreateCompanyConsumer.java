package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.CreateCompanyModel;
import org.hrms.service.CompanyService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCompanyConsumer {

    private final CompanyService companyService;

    @RabbitListener(queues = "${rabbitmq.create-company-queue}")
    public void createCompany(CreateCompanyModel model) {
        companyService.createCompany(model);
    }
}
