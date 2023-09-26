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
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long authid;
    private String comment;
    private String companyName;


    @Enumerated(EnumType.STRING)
    private EUserType userType;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EStatus status= EStatus.PENDING;
}
