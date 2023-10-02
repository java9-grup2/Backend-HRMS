package org.hrms.service;

import org.hrms.repository.IExpenseRepository;
import org.hrms.repository.entity.Expense;
import org.hrms.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService extends ServiceManager<Expense,Long> {
    private final IExpenseRepository repository;

    public ExpenseService(IExpenseRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
