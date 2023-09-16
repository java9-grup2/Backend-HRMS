package org.hrms.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserModel implements Serializable {

    private Long id;
    private String username;
    private String password;
    private String personalEmail;
    private String taxNo;
    private String companyName;
}
