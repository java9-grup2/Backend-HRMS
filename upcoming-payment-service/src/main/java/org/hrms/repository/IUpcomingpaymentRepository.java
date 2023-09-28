package org.hrms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUpcomingpaymentRepository extends JpaRepository<Upcomingpayment, Long> {
}
