package com.company.service;

import com.company.dto.like.LikeAndDislikeDTO;
import com.company.dto.like.LikeDTO;
import com.company.entity.ArticleEntity;
import com.company.entity.CommentEntity;
import com.company.entity.LikeEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.LikeStatus;
import com.company.enums.LikeType;
import com.company.exeptions.BadRequestException;
import com.company.exeptions.ItemNotFoundException;
import com.company.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;

    public LikeDTO create(LikeDTO dto, Integer profileId) {
        ProfileEntity profileEntity = profileService.get(profileId);
        if (dto.getStatus() == null || dto.getType() == null || dto.getActionId() == null) {
            throw new BadRequestException("Like can not be null");
        }
        LikeEntity entity = new LikeEntity();
        entity.setStatus(dto.getStatus());
        entity.setProfile(profileEntity);
        entity.setType(dto.getType());
        entity.setActionId(dto.getActionId());

        likeRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public LikeAndDislikeDTO articleLikeAndDislikeCount(Integer articleId, LikeType type) {
        ArticleEntity articleEntity = articleService.get(articleId);
        if (articleEntity == null || !type.equals(LikeType.ARTICLE_ID)) {
            throw new BadRequestException("Article not found");
        }
        LikeAndDislikeDTO dto = new LikeAndDislikeDTO();
        dto.setLikeCount(likeRepository.likeOrDislikeCount(articleId, type, LikeStatus.LIKE));
        dto.setDislikeCount(likeRepository.likeOrDislikeCount(articleId, type, LikeStatus.DISLIKE));
        return dto;
    }

    public LikeAndDislikeDTO commentLikeAndDislikeCount(Integer commentId, LikeType type) {
        CommentEntity commentEntity = commentService.get(commentId);
        if (commentEntity == null || !type.equals(LikeType.COMMENT_ID)) {
            throw new BadRequestException("Article not found");
        }
        LikeAndDislikeDTO dto = new LikeAndDislikeDTO();
        dto.setLikeCount(likeRepository.likeOrDislikeCount(commentId, type, LikeStatus.LIKE));
        dto.setDislikeCount(likeRepository.likeOrDislikeCount(commentId, type, LikeStatus.DISLIKE));
        return dto;
    }

    public List<String> likedArticle(Integer profileId) {
        ProfileEntity profileEntity = profileService.get(profileId);
        List<LikeEntity> entityList = likeRepository.findByProfile(profileEntity);
        if (!entityList.isEmpty()) {
            throw new BadRequestException("entity can not be empty");
        }
        List<String> articleTitleList = new LinkedList<>();
        for (LikeEntity entity : entityList) {
            if (entity.getType().equals(LikeType.ARTICLE_ID)) {
                ArticleEntity articleEntity = articleService.get(entity.getActionId());
                articleTitleList.add(articleEntity.getTitle());
            }
        }
        return articleTitleList;
    }

    public boolean update(Integer profileId, Integer likeId, LikeDTO dto) {
        LikeEntity entity = get(likeId);
        if (!entity.getProfile().getId().equals(profileId)) {
            throw new BadRequestException("Not owner");
        }
        entity.setStatus(dto.getStatus());
        likeRepository.save(entity);
        return true;
    }

    public boolean delete(Integer profileId, Integer likeId) {
        LikeEntity entity = get(likeId);
        if (!entity.getProfile().getId().equals(profileId)) {
            throw new BadRequestException("Not owner");
        }
        likeRepository.delete(entity);
        return true;
    }

    public LikeDTO toDTO(LikeEntity entity) {
        LikeDTO dto = new LikeDTO();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setActionId(entity.getActionId());
        dto.setType(entity.getType());
        dto.setProfileId(entity.getProfile().getId());
        dto.setCreatedAd(entity.getCreatedDate());
        return dto;
    }

    public LikeEntity get(Integer id) {
        return likeRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Comment Not Found"));
    }
}
