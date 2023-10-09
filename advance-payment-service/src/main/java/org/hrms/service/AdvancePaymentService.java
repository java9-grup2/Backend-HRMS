package org.hrms.service;

import org.hrms.repository.IAdvancePaymentRepository;
import org.hrms.repository.entity.AdvancePayment;
import org.hrms.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class AdvancePaymentService extends ServiceManager<AdvancePayment,Long> {

    private final IAdvancePaymentRepository repository;

    public AdvancePaymentService(IAdvancePaymentRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
