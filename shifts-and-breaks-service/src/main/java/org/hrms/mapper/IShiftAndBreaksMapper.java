package org.hrms.mapper;

import org.hrms.dto.request.CreateShiftsAndBreaksRequestDto;
import org.hrms.repository.IShiftsAndBreaksRepository;
import org.hrms.repository.entity.ShiftsAndBreaks;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IShiftAndBreaksMapper {
    IShiftAndBreaksMapper INSTANCE = Mappers.getMapper(IShiftAndBreaksMapper.class);

    ShiftsAndBreaks toShiftsAndBreaks(final CreateShiftsAndBreaksRequestDto dto);
}
