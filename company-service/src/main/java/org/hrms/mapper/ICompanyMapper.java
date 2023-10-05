package org.hrms.mapper;

import org.hrms.dto.request.SaveCompanyRequestDto;
import org.hrms.dto.response.ContactInformationResponseDto;
import org.hrms.rabbitmq.model.CreateCompanyModel;
import org.hrms.rabbitmq.model.DeleteUsersContainsCompanyNameModel;
import org.hrms.repository.ICompanyRepository;
import org.hrms.repository.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICompanyMapper {

    ICompanyMapper INSTANCE = Mappers.getMapper(ICompanyMapper.class);

    Company toCompany(final CreateCompanyModel model);

    DeleteUsersContainsCompanyNameModel toDeleteUsersContainsCompanyNameModel(final Company company);

    ContactInformationResponseDto toContactInformationResponseDto(final Company company);
}
