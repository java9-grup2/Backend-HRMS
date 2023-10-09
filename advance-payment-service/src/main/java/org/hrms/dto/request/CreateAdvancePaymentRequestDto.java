package org.hrms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hrms.repository.enums.EStatus;
import org.hrms.repository.enums.EUserType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdvancePaymentRequestDto {

    private String token;
    private String companyName;

    @NotNull(message = "avans mikari bos birakilamaz")
    private Double amount;

    private EUserType userType;
}
