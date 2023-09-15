package org.hrms.repository;

import org.hrms.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {

    boolean existsByPersonalEmail(String personalEmail);

    boolean existsByUsername(String username);

}
