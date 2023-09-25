package org.hrms.mapper;

import org.hrms.dto.request.CreateFinancialPerformanceRequestDto;
import org.hrms.repository.entity.FinancialPerformance;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IFinancialPerformanceMapper {

    IFinancialPerformanceMapper INSTANCE = Mappers.getMapper(IFinancialPerformanceMapper.class);

    FinancialPerformance toFinancialPerformance(final CreateFinancialPerformanceRequestDto dto);
}
