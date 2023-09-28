package org.hrms.mapper;

import org.hrms.dto.CompanyNameToStringDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUpcomingPaymentMapper {
    IUpcomingPaymentMapper INSTANCE = Mappers.getMapper(IUpcomingPaymentMapper.class);

    String toString(final CompanyNameToStringDto dto);



}
