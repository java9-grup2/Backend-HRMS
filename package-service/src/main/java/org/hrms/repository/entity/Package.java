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
    private Long authid;

    @Enumerated(EnumType.STRING)
    private EPackageType packageType;

    private Double price;
    private LocalDate publishDate;
    private LocalDate takeDownDate;
    private Long userCount;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private EStatus status = EStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private ECurrencyType currencyType;


}
