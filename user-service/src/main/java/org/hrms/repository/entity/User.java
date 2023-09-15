package org.hrms.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hrms.repository.enums.EStatus;
import org.hrms.repository.enums.EUserType;
import javax.persistence.*;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_user")
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long authid;
    private String username;
    private String password;
    @Column(unique = true)
    private String personalEmail;
    @Column(unique = true)
    private String companyEmail;
    private String activationCode;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    EUserType userType = EUserType.VISITOR;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    EStatus status = EStatus.PENDING;

    String taxNo;
    String companyName;

}
