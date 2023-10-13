package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.DenyCompanyPackageModel;
import org.hrms.service.PackageService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DenyCompanyPackageConsumer {

    private final PackageService service;

    @RabbitListener(queues = "${rabbitmq.deny-company-package-queue}")
    public void denyCompanyPackage(DenyCompanyPackageModel model) {
        service.denyStatus(model);
    }
}
