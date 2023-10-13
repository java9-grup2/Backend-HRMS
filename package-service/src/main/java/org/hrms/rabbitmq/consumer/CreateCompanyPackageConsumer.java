package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.CreateCompanyPackageModel;
import org.hrms.service.PackageService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCompanyPackageConsumer {

    private final PackageService service;

    @RabbitListener(queues = "${rabbitmq.create-company-package-queue}")
    public void createCompanyPackage(CreateCompanyPackageModel model) {
        service.createPackage(model);
    }
}
