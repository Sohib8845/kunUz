package com.company.spec;

import com.company.entity.ArticleEntity;
import com.company.enums.ArticleStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

//Comment filter: commentId, profileId, articleId, createdDate (fromDate, toDate),
//        Pagination, orderByFiled [asc,desc]

public class CommentSpecification {


    public static Specification<ArticleEntity> status(ArticleStatus status){
        return (((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("status"),status);
        }));
    }

    public static Specification<ArticleEntity> title(String title){
        return (((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("title"),title);
        }));
    }

    public static Specification<ArticleEntity> equal(String field,Integer id){
        return (((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(field),id);
        }));
    }

    public static Specification<ArticleEntity> fromdate(LocalDate date){
        return (((root, query, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("fromDate"),date);
        }));
    }

    public static Specification<ArticleEntity> todate(LocalDate date){
        return (((root, query, criteriaBuilder) -> {
            return criteriaBuilder.lessThanOrEqualTo(root.get("toDate"),date);
        }));
    }


}
