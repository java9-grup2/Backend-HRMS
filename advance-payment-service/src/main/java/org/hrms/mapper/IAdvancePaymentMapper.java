package org.hrms.mapper;

import org.hrms.dto.request.AdvancePaymentUserControlDto;
import org.hrms.dto.request.CreateAdvancePaymentRequestDto;
import org.hrms.repository.entity.AdvancePayment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAdvancePaymentMapper {

    IAdvancePaymentMapper INSTANCE = Mappers.getMapper(IAdvancePaymentMapper.class);

    AdvancePaymentUserControlDto toAdvancePaymentUserControlDto(final CreateAdvancePaymentRequestDto dto);

    AdvancePayment toAdvancePayment(final CreateAdvancePaymentRequestDto dto);
}
