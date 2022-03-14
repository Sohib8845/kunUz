package com.company.controller;

import com.company.dto.like.LikeAndDislikeDTO;
import com.company.dto.like.LikeDTO;
import com.company.dto.profile.ProfileJwtDTO;
import com.company.enums.LikeType;
import com.company.service.LikeService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody LikeDTO dto, HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        LikeDTO response = likeService.create(dto, jwtDTO.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/articleId/{id}")
    public ResponseEntity<?> articleLikeAndDislikeCount(@PathVariable("id") Integer articleId,
                                                        @RequestParam("type") LikeType type) {
        LikeAndDislikeDTO count = likeService.articleLikeAndDislikeCount(articleId, type);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/commentId/{id}")
    public ResponseEntity<?> commentLikeAndDislikeCount(@PathVariable("id") Integer commentId,
                                                        @RequestParam("type") LikeType type) {
        LikeAndDislikeDTO count = likeService.commentLikeAndDislikeCount(commentId, type);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/likedArticle/{id}")
    public ResponseEntity<?> likedArticle(@PathVariable("id") Integer profileId, HttpServletRequest request) {
        JwtUtil.getProfile(request);
        List<String> articleLikedList = likeService.likedArticle(profileId);
        return ResponseEntity.ok(articleLikedList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody LikeDTO dto, HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        boolean result = likeService.update(jwtDTO.getId(), id, dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id, HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.getProfile(request);
        boolean result = likeService.delete(jwtDTO.getId(), id);
        return ResponseEntity.ok(result);
    }
}
