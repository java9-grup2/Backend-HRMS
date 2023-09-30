package org.hrms.mapper;

import org.hrms.dto.request.RegisterVisitorRequestDto;
import org.hrms.rabbitmq.model.*;
import org.hrms.repository.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {
    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);
    User toUser (final RegisterVisitorRequestDto dto);

    User toUser(final RegisterVisitorModel model);

    User toUser(final RegisterManagerModel model);

    User toUser(final SaveEmployeeModel model);

    @Mapping(source = "authid",target = "id")
    UpdateUserModel toUpdateUserModel(final User user);

    ApproveManagerMailModel toApproveManagerMailModel(final User user);

    User toUser(final CreateAdminUserModel model);

    DeleteAuthContainsCompanyNameModel toDeleteAuthContainsCompanyNameModel(final DeleteUsersContainsCompanyNameModel model);

    ActivateCompanyStatusModel toActivateCompanyStatusModel(final User user);
}
