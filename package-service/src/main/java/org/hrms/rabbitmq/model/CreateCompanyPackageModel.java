package org.hrms.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hrms.repository.enums.EPackageType;
import org.hrms.repository.enums.EUserType;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCompanyPackageModel implements Serializable {

    private EUserType userType;

    private String companyName;

    private EPackageType packageType;

}
