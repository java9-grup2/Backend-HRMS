package org.hrms.repository;

import org.hrms.repository.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExpenseRepository extends JpaRepository<Expense,Long> {


}
