package org.hrms.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hrms.repository.enums.ECurrencyType;
import org.hrms.repository.enums.EPackageType;
import org.hrms.repository.enums.EStatus;

import javax.persistence.*;
import java.time.LocalDate;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Package extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EPackageType packageType=EPackageType.SILVER;

    private Double price;
    private LocalDate activateDate;
    private LocalDate endDate;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private EStatus status = EStatus.PENDING;

}
