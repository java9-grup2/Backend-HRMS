package org.hrms.repository;

import org.hrms.repository.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthRepository extends JpaRepository<Auth,Long> {


    boolean existsByPersonalEmail(String personalEmail);

    boolean existsByUsername(String username);

    Optional<Auth> findByPersonalEmailAndPassword(String email, String password);
    Optional<Auth> findByCompanyEmailEmailAndPassword(String companyEmail, String password);

    boolean existsByCompanyEmail(String companyEmail);
}
