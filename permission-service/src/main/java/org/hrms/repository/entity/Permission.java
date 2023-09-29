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
    private EUserType userType;
    TypeOfPermit typeOfPermit;
    LocalDate startDate;
    LocalDate endDate;
    @Builder.Default
    LocalDate dateOfRequest=LocalDate.now();
    Integer numberOfDays; //kendim metotla yazmalıyım
    ApprovalStatus approvalStatus;
    LocalDate replyDate;
    //name and surname
}
