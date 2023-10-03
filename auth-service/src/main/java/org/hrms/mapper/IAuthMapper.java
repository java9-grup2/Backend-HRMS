package org.hrms.mapper;

import org.hrms.dto.request.IsCompanyRequestValidDto;
import org.hrms.dto.request.RegisterManagerRequestDto;
import org.hrms.dto.request.RegisterVisitorRequestDto;
import org.hrms.rabbitmq.model.*;
import org.hrms.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {
    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);


    Auth toAuth(final RegisterVisitorRequestDto dto);

    Auth toAuth(final RegisterManagerRequestDto dto);

    @Mapping(source = "id",target = "authid")
    RegisterVisitorModel toRegisterVisitorModel(final Auth auth);

    @Mapping(source = "id",target = "authid")
    RegisterManagerModel toRegisterManagerModel(final Auth auth);

    ActivationMailModel toActivationMailModel(final Auth auth);

    RegisterEmployeeMailModel toRegisterEmployeeModel(final Auth auth);

    @Mapping(target = "authid",source = "id")
    SaveEmployeeModel toSaveEmployeeModel(final Auth auth);

    @Mapping(target = "authid",source = "id")
    ActivateStatusModel toActivateStatusModel(final Auth auth);

    @Mapping(source = "id",target = "managerId")
    CreateCompanyModel toCreateCompanyModel(final Auth auth);

    @Mapping(target = "authid",source = "id")
    CreateAdminUserModel toCreateAdminUserModel(final Auth auth);

    IsCompanyRequestValidDto toIsCompanyRequestValidDto(final RegisterManagerRequestDto dto);
}
