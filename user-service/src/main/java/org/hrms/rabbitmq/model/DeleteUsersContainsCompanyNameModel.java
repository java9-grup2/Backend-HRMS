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
public class DeleteUsersContainsCompanyNameModel implements Serializable {

    private String companyName;
}
