package org.hrms.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hrms.repository.enums.EStatus;
import org.hrms.repository.enums.EUserType;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdminUserModel implements Serializable {

    private Long authid;
    private String password;
    private String personalEmail;
    private String companyName;
    EUserType userType;
    EStatus status;

}
