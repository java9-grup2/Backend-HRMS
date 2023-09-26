package org.hrms.dto.response;

import org.hrms.repository.enums.ApprovalStatus;
import org.hrms.repository.enums.TypeOfPermit;
import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApprovalStatusResponse {

    Long workerid;
    TypeOfPermit typeOfPermit;
    LocalDate startDate;
    LocalDate endDate;
    LocalDate dateOfRequest;
    Integer numberOfDays;
    ApprovalStatus approvalStatus;
}
