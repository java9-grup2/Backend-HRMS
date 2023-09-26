package org.hrms.dto.response;

import org.hrms.repository.enums.ApprovalStatus;
import org.hrms.repository.enums.TypeOfPermit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WorkerPermissionForManager {

    Long id;
    Long workerid;
    TypeOfPermit typeOfPermit;
    LocalDate startDate;
    LocalDate endDate;
    LocalDate dateOfRequest;
    Integer numberOfDays;
    ApprovalStatus approvalStatus;
    LocalDate replyDate;
    String name;
    String surname;
}
