package org.hrms.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.IncreaseCompanyWorkerModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncreaseCompanyWorkerProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.authExchange}")
    private String authExchange;

    @Value("${rabbitmq.increase-company-worker-bindingKey}")
    private String increaseCompanyWorkerBindingKey;

    public void increaseCompanyWorker(IncreaseCompanyWorkerModel model) {
        rabbitTemplate.convertAndSend(authExchange,increaseCompanyWorkerBindingKey,model);
    }
}
