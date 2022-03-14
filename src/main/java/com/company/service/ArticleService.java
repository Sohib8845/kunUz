package com.company.service;

import com.company.dto.article.ArticleDTO;
import com.company.dto.article.ArticleFilterDTO;
import com.company.entity.ArticleEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ArticleStatus;
import com.company.repository.ArticleCustomRepositoryImpl;
import com.company.repository.ArticleRepository;
import com.company.spec.ArticleSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ArticleCustomRepositoryImpl customRepository;

    public ArticleDTO create(ArticleDTO dto,Integer userId) {
            ProfileEntity profile = profileService.get(userId);
            ArticleEntity entity=new ArticleEntity();
            entity.setContent(dto.getContent());
            entity.setTitle(dto.getTitle());
            entity.setCreatedDate(LocalDateTime.now());
            entity.setProfile(profile);
            entity.setStatus(ArticleStatus.PUBLISHED);
            articleRepository.save(entity);
            dto.setId(entity.getId());
            dto.setCreatedDate(entity.getCreatedDate());
            return dto;
    }

    public String update(ArticleDTO dto,Integer id){
        articleRepository.UpdateArticleById(dto.getTitle(), dto.getContent(), id);
        return "Updated";
    }

    public String delete(Integer id){
        articleRepository.DeleteByArticleId(id);
        return "Deleted";
    }

    public ArticleDTO getById(Integer id){
        ArticleEntity entity = articleRepository.getById(id);
        return toDto(entity);
    }

    public ArticleEntity get(Integer id){
        ArticleEntity entity = articleRepository.getById(id);
        return entity;
    }

    public PageImpl<ArticleDTO> list(Integer page,Integer size){
        Pageable paging = PageRequest.of(page, size, Sort.Direction.DESC, "createdDate");
        Page<ArticleEntity> entityPage = articleRepository.findAll(paging);

        long totalElements = entityPage.getTotalElements();

        List<ArticleEntity> entityList = entityPage.getContent();
        List<ArticleDTO> dtoList = new LinkedList<>();
        for (ArticleEntity entity : entityList) {
            dtoList.add(toDto(entity));
        }

        return new PageImpl<>(dtoList, paging, totalElements);
    }

    public ArticleDTO toDto(ArticleEntity entity){
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPublishedDate(entity.getPublishedDate());
        return dto;
    }


    public PageImpl<ArticleDTO> filter(int page, int size, ArticleFilterDTO filterDTO) {
        PageImpl<ArticleEntity> entityPage = customRepository.filter(page, size, filterDTO);

        List<ArticleDTO> articleDTOList = entityPage.stream().map(articleEntity -> {
            ArticleDTO dto = new ArticleDTO();
            dto.setId(articleEntity.getId());
            dto.setContent(articleEntity.getContent());
            dto.setTitle(articleEntity.getTitle());
            dto.setCreatedDate(articleEntity.getCreatedDate());
            return dto;
        }).collect(Collectors.toList());

        return new PageImpl<>(articleDTOList, entityPage.getPageable(), entityPage.getTotalElements());
    }

    public void filter2(int page,int size,ArticleFilterDTO filterDTO) {
        customRepository.filterCriteriaBuilder(page,size,filterDTO);
    }

    public PageImpl<ArticleDTO> filterSpec(int page, int size, ArticleFilterDTO filterDTO) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");

        Specification<ArticleEntity> spec = null;
        if (filterDTO.getStatus() != null) {
            spec = Specification.where(ArticleSpecification.status(filterDTO.getStatus()));
        } else {
            spec = Specification.where(ArticleSpecification.status(ArticleStatus.PUBLISHED));
        }

        if (filterDTO.getTitle() != null) {
            spec.and(ArticleSpecification.title(filterDTO.getTitle()));
        }
        if (filterDTO.getArticleId() != null) {
            spec.and(ArticleSpecification.equal("id", filterDTO.getArticleId()));
        }
        if (filterDTO.getProfileId() != null) {
            spec.and(ArticleSpecification.equal("profile.id", filterDTO.getProfileId()));
        }

        Page<ArticleEntity> entityPage = articleRepository.findAll(spec, pageable);

        List<ArticleDTO> articleDTOList = entityPage.stream().map(articleEntity -> {
            ArticleDTO dto = new ArticleDTO();
            dto.setId(articleEntity.getId());
            dto.setContent(articleEntity.getContent());
            dto.setTitle(articleEntity.getTitle());
            dto.setCreatedDate(articleEntity.getCreatedDate());
            return dto;
        }).collect(Collectors.toList());
        return new PageImpl<>(articleDTOList,entityPage.getPageable(),entityPage.getTotalElements());
    }

    public PageImpl<ArticleDTO> filterCriteriaBuilder(int page,int size,ArticleFilterDTO filterDTO){
        PageImpl<ArticleEntity> entityPage = customRepository.filterCriteriaBuilder(page, size, filterDTO);

        List<ArticleDTO> articleDTOList = entityPage.stream().map(articleEntity -> {
            ArticleDTO dto = new ArticleDTO();
            dto.setId(articleEntity.getId());
            dto.setContent(articleEntity.getContent());
            dto.setTitle(articleEntity.getTitle());
            dto.setCreatedDate(articleEntity.getCreatedDate());
            return dto;
        }).collect(Collectors.toList());

        return new PageImpl<>(articleDTOList, entityPage.getPageable(), entityPage.getTotalElements());
    }



}
