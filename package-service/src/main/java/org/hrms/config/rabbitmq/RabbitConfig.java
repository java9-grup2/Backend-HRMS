package org.hrms.config.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.create-company-package-queue}")
    private String createCompanyPackageQueue;

    @Value("${rabbitmq.activate-company-package-queue}")
    private String activateCompanyPackageQueue;

    @Value("${rabbitmq.deny-company-package-queue}")
    private String denyCompanyPackageQueue;


    @Bean
    Queue denyCompanyPackageQueue() {
        return new Queue(denyCompanyPackageQueue);
    }

    @Bean
    Queue createCompanyPackageQueue() {
        return new Queue(createCompanyPackageQueue);
    }

    @Bean
    Queue activateCompanyPackageQueue() {
        return new Queue(activateCompanyPackageQueue);
    }
}
