package org.hrms.mapper;


import org.hrms.dto.request.CreateExpenseRequestDto;
import org.hrms.repository.entity.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IExpenseMapper {

    IExpenseMapper INSTANCE = Mappers.getMapper(IExpenseMapper.class);

    Expense toExpense(final CreateExpenseRequestDto dto);
}
