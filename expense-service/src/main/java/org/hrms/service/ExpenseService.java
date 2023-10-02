package org.hrms.service;

import org.hrms.dto.request.CreateExpenseRequestDto;
import org.hrms.dto.response.CreateExpenseResponseDto;
import org.hrms.mapper.IExpenseMapper;
import org.hrms.repository.IExpenseRepository;
import org.hrms.repository.entity.Expense;
import org.hrms.utility.JwtTokenManager;
import org.hrms.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService extends ServiceManager<Expense,Long> {
    private final IExpenseRepository repository;
    private final JwtTokenManager jwtTokenManager;


    public ExpenseService(IExpenseRepository repository, JwtTokenManager jwtTokenManager) {
        super(repository);
        this.repository = repository;
        this.jwtTokenManager = jwtTokenManager;
    }

    public CreateExpenseResponseDto createExpense(CreateExpenseRequestDto dto){
        Expense expense= IExpenseMapper.INSTANCE.toExpense(dto);
        expense.setAuthid(jwtTokenManager.getIdFromToken(dto.getToken()).get());
        save(expense);
        return new CreateExpenseResponseDto("Harcama talebi başarıyla eklendi");
    }



}
