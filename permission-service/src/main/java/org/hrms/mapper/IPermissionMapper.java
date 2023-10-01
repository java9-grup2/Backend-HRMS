package org.hrms.mapper;

import org.hrms.dto.request.AuthIdAndCompanyNameCheckerRequestDto;
import org.hrms.dto.request.CreateDayOffRequestDto;
import org.hrms.repository.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IPermissionMapper {

    IPermissionMapper INSTANCE = Mappers.getMapper(IPermissionMapper.class);


    AuthIdAndCompanyNameCheckerRequestDto toAuthIdAndCompanyNameCheckerRequestDto(final CreateDayOffRequestDto dto);

    Permission toPermission(final CreateDayOffRequestDto dto);
}
