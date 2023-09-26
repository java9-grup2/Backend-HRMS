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
public class WorkerPermissionForWorker {

    Long id;
    TypeOfPermit typeOfPermit;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate endDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate dateOfRequest;
    Integer numberOfDays;
    ApprovalStatus approvalStatus;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate replyDate;
}
