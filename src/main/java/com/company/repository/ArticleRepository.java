package com.company.repository;

import com.company.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer>,
        JpaSpecificationExecutor<ArticleEntity> {


    @Transactional
    @Modifying
    @Query("UPDATE ArticleEntity a SET a.title=:title , a.content=:content WHERE a.id=:id ")
    void UpdateArticleById(@Param("title") String title,@Param("content") String content,
                                    @Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("DELETE FROM ArticleEntity a WHERE a.id=:id ")
    void DeleteByArticleId(@Param("id") Integer id);



}
