package org.hrms.mapper;


import org.hrms.dto.request.CreatePermissionRequestDto;
import org.hrms.dto.response.PersonelPermissionResponseDto;
import org.hrms.repository.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IPermissionMapper {
    IPermissionMapper INSTANCE = Mappers.getMapper(IPermissionMapper.class);

    PersonelPermissionResponseDto entityToDto(Permission entity);
}
