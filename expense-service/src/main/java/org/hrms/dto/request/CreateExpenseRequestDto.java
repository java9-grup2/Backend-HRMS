package org.hrms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hrms.repository.enums.EExpenseType;
import org.hrms.repository.enums.EUserType;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateExpenseRequestDto {
    String token;
    EUserType userType;
    String companyName;
    Double amount;
    EExpenseType expenseType;
    String currency;
    String file;
}
