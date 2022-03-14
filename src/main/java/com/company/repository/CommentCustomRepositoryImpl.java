package com.company.repository;

import com.company.dto.comment.CommentFilterDTO;
import com.company.entity.ArticleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentCustomRepositoryImpl {
    @Autowired
    private EntityManager entityManager;

    public PageImpl filter(int page, int size, CommentFilterDTO filterDTO){
        Map<String,Object> params = new HashMap<>();

        StringBuilder builder = new StringBuilder("SELECT c FROM CommentEntity c");
        StringBuilder builderCount = new StringBuilder("SELECT count(c) FROM CommentEntity c ");

        if(filterDTO.getCoommentId() != null){
            builder.append("where c.id=:id");
            builderCount.append("where c.id=:id");
            params.put("id",filterDTO.getCoommentId());
        }

        if(filterDTO.getArticleId() != null){
            builder.append(" and where c.article.id=:id");
            builderCount.append(" and where c.article.id=:id");
            params.put("id",filterDTO.getArticleId());
        }

        if(filterDTO.getProfileId() != null){
            builder.append(" and where c.profile.id =: id");
            builderCount.append(" and where c.profile.id=:id");
            params.put("id",filterDTO.getProfileId());
        }

        if(filterDTO.getFromDate() != null){
            builder.append(" and where c.createdDate >=: fromDate");
            builderCount.append(" and where c.createdDate >=: fromDate");
            params.put("fromDate",filterDTO.getFromDate());
        }

        if(filterDTO.getToDate() != null){
            builder.append(" and where c.createdDate <=: toDate");
            builderCount.append(" and where c.createdDate <=: toDate");
            params.put("toDate",filterDTO.getToDate());
        }

        Query query = entityManager.createQuery((builder.toString()));
        //TODO so'riman
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);

        for(Map.Entry<String,Object> entrySet : params.entrySet()){
            query.setParameter(entrySet.getKey(),entrySet.getValue());
        }
        List<ArticleEntity> articleList = query.getResultList();


        query = entityManager.createQuery(builderCount.toString());
        for (Map.Entry<String, Object> entrySet : params.entrySet()) {
            query.setParameter(entrySet.getKey(), entrySet.getValue());
        }
        Long totalCount = (Long) query.getSingleResult();

        return new PageImpl(articleList, PageRequest.of(page, size), totalCount);
    }

}
