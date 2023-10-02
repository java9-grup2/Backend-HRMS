package org.hrms.repository.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.asm.Advice;
import org.hrms.repository.enums.ApprovalStatus;
import org.hrms.repository.enums.EUserType;
import org.hrms.repository.enums.TypeOfPermit;
import javax.persistence.*;
import java.time.LocalDate;

@SuperBuilder // bir sınıftan nesne türetmeyi sağlar
@Data //get set metodlarını otomatik tanımlar
@NoArgsConstructor // boş constructor
@AllArgsConstructor // dolu constructor oluştur
@ToString
@Entity
@Table(name = "permission")
public class Permission extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long authid;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    TypeOfPermit typeOfPermit = TypeOfPermit.ANNUAL;
    LocalDate startDate;
    LocalDate endDate;

    private String companyName;

    @Builder.Default
    LocalDate dateOfRequest=LocalDate.now();
    Long numberOfDays;

    LocalDate replyDate;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    ApprovalStatus approvalStatus = ApprovalStatus.PENDING;
}
