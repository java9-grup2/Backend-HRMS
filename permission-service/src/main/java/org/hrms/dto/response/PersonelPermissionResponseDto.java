package org.hrms.dto.response;

import org.hrms.repository.enums.ApprovalStatus;
import org.hrms.repository.enums.TypeOfPermit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PersonelPermissionResponseDto {
    Long id;
    String username;
    LocalDate startDate;
    LocalDate endDate;
    TypeOfPermit typeOfPermit;
    ApprovalStatus approvalStatus;
}
