package org.hrms.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.hrms.rabbitmq.model.DeleteCompanyByRegisterDenyModel;
import org.hrms.service.CompanyService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCompanyByRegisterDenyConsumer {

    private final CompanyService companyService;

    @RabbitListener(queues = "${rabbitmq.delete-company-by-register-deny-Queue}")
    public void deleteCompanyByName(DeleteCompanyByRegisterDenyModel model) {
        companyService.deleteByCompanyName(model);
    }
}
