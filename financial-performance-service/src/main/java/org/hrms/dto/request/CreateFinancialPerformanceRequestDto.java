package org.hrms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFinancialPerformanceRequestDto {

    @NotBlank
    private String companyName;
    @NotNull
    private LocalDate financialYear;
    @NotNull
    private Double annualRevenue;
    @NotNull
    private Double annualExpenses;
    @NotBlank
    private String userType;
}
