package org.hrms.mapper;

import org.hrms.dto.request.RegisterVisitorRequestDto;
import org.hrms.repository.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {
    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);
    User toUser (final RegisterVisitorRequestDto dto);

}
