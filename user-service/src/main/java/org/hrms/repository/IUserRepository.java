package org.hrms.repository;

import org.hrms.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {

    boolean existsByPersonalEmail(String personalEmail);

    boolean existsByUsername(String username);

    boolean existsByCompanyEmail(String companyEmail);

    Optional<User> findByAuthid(Long authid);

}
