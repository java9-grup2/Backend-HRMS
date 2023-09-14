package org.hrms.mapper;

import org.hrms.dto.request.RegisterManagerRequestDto;
import org.hrms.dto.request.RegisterVisitorRequestDto;
import org.hrms.rabbitmq.model.ActivationMailModel;
import org.hrms.rabbitmq.model.RegisterManagerModel;
import org.hrms.rabbitmq.model.RegisterVisitorModel;
import org.hrms.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {
    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);


    Auth toAuth(final RegisterVisitorRequestDto dto);

    Auth toAuth(final RegisterManagerRequestDto dto);

    RegisterVisitorModel toRegisterVisitorModel(final Auth auth);

    RegisterManagerModel toRegisterManagerModel(final Auth auth);

    ActivationMailModel toActivationMailModel(final Auth auth);
}
