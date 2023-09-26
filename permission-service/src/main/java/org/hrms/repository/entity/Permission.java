package org.hrms.repository.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hrms.repository.enums.ApprovalStatus;
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
    Long managerid;
    Long workerid;
    TypeOfPermit typeOfPermit;
    LocalDate startDate;
    LocalDate endDate;
    LocalDate dateOfRequest;
    Integer numberOfDays;
    ApprovalStatus approvalStatus;
    LocalDate replyDate;
    String name;
    String surname;
}
