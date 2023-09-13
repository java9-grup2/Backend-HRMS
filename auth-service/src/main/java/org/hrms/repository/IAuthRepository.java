package org.hrms.repository;

import org.hrms.repository.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthRepository extends JpaRepository<Auth,Long> {


    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<Auth> findByEmailAndPassword(String email, String password);
}
