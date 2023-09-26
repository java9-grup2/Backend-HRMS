package org.hrms.dto.request;
import lombok.*;
import org.hrms.repository.enums.ApprovalStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateStatusRequestDto {

    Long id;
    ApprovalStatus status;

}

