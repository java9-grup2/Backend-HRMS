package org.hrms.repository;

import org.hrms.repository.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comment,Long> {

    @Query("select  c from Comment  c where c.status = 'PENDING'")
    List<Comment> getAllPendingComments();
}
