package com.company.service;

import com.company.dto.article.ArticleDTO;
import com.company.dto.article.ArticleFilterDTO;
import com.company.dto.comment.CommentDTO;
import com.company.dto.comment.CommentFilterDTO;
import com.company.entity.ArticleEntity;
import com.company.entity.CommentEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ArticleStatus;
import com.company.exeptions.BadRequestException;
import com.company.repository.ArticleCustomRepositoryImpl;
import com.company.repository.CommentCustomRepositoryImpl;
import com.company.repository.CommentRepository;
import com.company.spec.ArticleSpecification;
import com.company.spec.CommentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentCustomRepositoryImpl customRepository;

    public CommentDTO create(CommentDTO dto,Integer userId,Integer articleId){
        ProfileEntity profile = profileService.get(userId);
        ArticleEntity article = articleService.get(articleId);

        CommentEntity entity=new CommentEntity();
        entity.setContent(dto.getContent());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setProfile(profile);
        entity.setArticle(article);
        commentRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public String update(Integer commentId,CommentDTO dto, Integer userId){
        CommentEntity entity = get(commentId);
        if(!entity.getProfile().getId().equals(userId)){
            throw new BadRequestException("Not owner");
        }
        entity.setContent(dto.getContent());
        commentRepository.save(entity);
        return "Updated";
    }

    public String delete(Integer commentId,Integer userId){
        CommentEntity entity = get(commentId);
        if(!entity.getProfile().getId().equals(userId)){
            throw new BadRequestException("Not owner");
        }
        commentRepository.deleteById(commentId);
        return "Deleted";
    }

    public List<CommentDTO> getList() {
        List<CommentDTO> dtoList=new LinkedList<>();
        List<CommentEntity> entityList = commentRepository.findAll();
        entityList.forEach(e->dtoList.add(toDto(e)));
        return dtoList;
    }

    public CommentDTO getByID(Integer id) {
        Optional<CommentEntity> entity = commentRepository.findById(id);
        return entity.map(this::toDto).orElseThrow(()->new RuntimeException("Not Found"));
    }

    public CommentEntity get(Integer id) {
        Optional<CommentEntity> optional = commentRepository.findById(id);
        return optional.get();
    }

    public CommentDTO toDto(CommentEntity entity){
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setProfileId(entity.getProfile().getId());
        dto.setArticleId(entity.getArticle().getId());
        return dto;
    }

    public PageImpl<CommentDTO> filter(int page, int size, CommentFilterDTO filterDTO) {
        PageImpl<CommentEntity> entityPage = customRepository.filter(page, size, filterDTO);

        List<CommentDTO> commentDTOList = entityPage.stream().map(commentEntity -> {
            CommentDTO dto = new CommentDTO();
            dto.setId(commentEntity.getId());
            return dto;
        }).collect(Collectors.toList());

        return new PageImpl<>(commentDTOList, entityPage.getPageable(), entityPage.getTotalElements());
    }

    public PageImpl<CommentDTO> filterSpec(int page, int size, CommentFilterDTO filterDTO) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");

        Specification<ArticleEntity> spec = null;

        if (filterDTO.getCoommentId() != null) {
            spec.and(ArticleSpecification.equal("id", filterDTO.getCoommentId()));
        }
        if (filterDTO.getProfileId() != null) {
            spec.and(ArticleSpecification.equal("profile.id", filterDTO.getProfileId()));
        }
        if (filterDTO.getFromDate() != null) {
            spec.and(CommentSpecification.fromdate(filterDTO.getFromDate()));
        }
        if (filterDTO.getToDate() != null) {
            spec.and(CommentSpecification.fromdate(filterDTO.getToDate()));
        }
//        findAll(spec, pageable);
        Page<CommentEntity> entityPage = commentRepository.findAll(pageable);

        List<CommentDTO> commentDTOList = entityPage.stream().map(commentEntity -> {
            CommentDTO dto = new CommentDTO();
            dto.setId(commentEntity.getId());
            dto.setContent(commentEntity.getContent());
            dto.setCreatedDate(commentEntity.getCreatedDate());
            return dto;
        }).collect(Collectors.toList());


        return new PageImpl<>(commentDTOList,entityPage.getPageable(),entityPage.getTotalElements());
    }
}
