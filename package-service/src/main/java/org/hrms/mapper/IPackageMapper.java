package org.hrms.mapper;

import org.hrms.dto.request.CreatePackageRequestDto;
import org.hrms.rabbitmq.model.CreateCompanyPackageModel;
import org.hrms.repository.entity.Package;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IPackageMapper {

    IPackageMapper INSTANCE = Mappers.getMapper(IPackageMapper.class);

    Package toPackage(final CreateCompanyPackageModel dto);
}
