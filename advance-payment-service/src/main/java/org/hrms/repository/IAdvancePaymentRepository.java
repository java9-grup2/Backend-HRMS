package org.hrms.repository;

import org.hrms.repository.entity.AdvancePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAdvancePaymentRepository extends JpaRepository<AdvancePayment,Long> {

    @Query("Select a from AdvancePayment a where a.status='PENDING' and a.companyName = :x ")
    List<AdvancePayment>listPendingAdvanceRequests(@Param("x") String companyName);
}
