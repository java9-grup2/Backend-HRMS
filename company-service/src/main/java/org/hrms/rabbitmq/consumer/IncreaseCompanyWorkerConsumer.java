package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.IncreaseCompanyWorkerModel;
import org.hrms.service.CompanyService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncreaseCompanyWorkerConsumer {

    private final CompanyService service;

    @RabbitListener(queues = "${rabbitmq.increase-company-worker-queue}")
    public void increaseCompanyWorker(IncreaseCompanyWorkerModel model) {
        service.increaseCompanyWorker(model);
    }
}
