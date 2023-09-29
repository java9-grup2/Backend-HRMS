package org.hrms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hrms.repository.enums.ApprovalStatus;
import org.hrms.repository.enums.EUserType;
import org.springframework.format.annotation.DateTimeFormat;
import org.hrms.repository.enums.TypeOfPermit;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreatePermissionRequestDto {

    Long authid;
    @Builder.Default    //belirtilmediği durumlarda yıllık izin
    TypeOfPermit typeOfPermit=TypeOfPermit.ANNUAL;
    LocalDate startDate;
    LocalDate endDate;

}


