package com.company.repository;

import com.company.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity,Integer>,
        JpaSpecificationExecutor<CommentEntity> {

    @Query("SELECT c FROM CommentEntity c WHERE c.article.id=?1")
    List<CommentEntity> getCommentByArticle(Integer id);

    @Query("SELECT c FROM CommentEntity c WHERE c.profile.id=?1")
    List<CommentEntity> getCommentByProfile(Integer id);

}
