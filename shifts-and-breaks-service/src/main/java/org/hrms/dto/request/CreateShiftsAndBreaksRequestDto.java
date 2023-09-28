package org.hrms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hrms.repository.enums.EShiftTypes;
import org.hrms.repository.enums.EUserType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateShiftsAndBreaksRequestDto {

    private String companyName;

    private EShiftTypes shiftTypes;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate shiftStartsAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate shiftEndsAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate breakStartsAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate breakEndsAt;

    private EUserType userType;
}
