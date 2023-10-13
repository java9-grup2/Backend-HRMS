package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.ActivateCompanyPackageModel;
import org.hrms.service.PackageService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivateCompanyPackageConsumer {

    private final PackageService service;

    @RabbitListener(queues = "${rabbitmq.activate-company-package-queue}")
    public void activateCompanyPackage(ActivateCompanyPackageModel model) {
        service.activateStatus(model);
    }
}
