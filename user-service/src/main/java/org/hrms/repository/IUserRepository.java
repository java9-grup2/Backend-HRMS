package org.hrms.repository;

import org.hrms.repository.entity.User;
import org.hrms.repository.enums.EUserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {

    boolean existsByPersonalEmail(String personalEmail);

    boolean existsByUsername(String username);

    boolean existsByCompanyEmail(String companyEmail);

    Optional<User> findByAuthid(Long authid);

    @Query("SELECT u FROM User u WHERE u.userType = 'EMPLOYEE' OR u.userType = 'MANAGER'")
    List<User> findAllWorkers();

    @Query("SELECT u FROM User u WHERE u.userType = 'EMPLOYEE'")
    List<User> findAllWorkersWithoutManager();

    @Query("Select u from User u where u.userType = 'MANAGER' AND  u.status = 'PENDING'")
    List<User> findAllPendingManagerApproval();

//    String findUsernameByAuthid(Long authid);


}
