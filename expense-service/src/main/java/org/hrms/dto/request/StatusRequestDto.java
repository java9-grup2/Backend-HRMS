package org.hrms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hrms.repository.enums.EUserType;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusRequestDto {

    private EUserType userType;
    private Long expenseId;
}
