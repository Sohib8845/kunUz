package com.company.spec;

import com.company.entity.ArticleEntity;
import com.company.enums.ArticleStatus;
import org.springframework.data.jpa.domain.Specification;

public class ArticleSpecification {

    public static Specification<ArticleEntity> status(ArticleStatus status){
        return (((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("status"),status);
        }));
    }

    public static Specification<ArticleEntity> title(String title){
        return (((root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("title"),title);
        }));
    }

    public static Specification<ArticleEntity> equal(String field,Integer id){
        return (((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(field),id);
        }));
    }
}
