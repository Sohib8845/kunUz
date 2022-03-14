package com.company.controller;

import com.company.dto.article.ArticleDTO;
import com.company.dto.article.ArticleFilterDTO;
import com.company.dto.profile.ProfileJwtDTO;
import com.company.enums.ProfileRole;
import com.company.service.ArticleService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ArticleDTO dto,
                                    HttpServletRequest token) {
        ProfileJwtDTO jwtDTO = JwtUtil.checkProfile(token, ProfileRole.MODERATOR_ROLE);

        ArticleDTO response = articleService.create(dto,jwtDTO.getId());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(HttpServletRequest token,
                                    @RequestBody ArticleDTO dto,
                                    @RequestParam("id") Integer articleId){
        JwtUtil.checkProfile(token, ProfileRole.MODERATOR_ROLE);
        return ResponseEntity.ok(articleService.update(dto,articleId));
    }

    @PutMapping("/delete")
    public ResponseEntity<?> delete(HttpServletRequest token,
                                    @RequestParam("id") Integer articleId){
        JwtUtil.checkProfile(token, ProfileRole.MODERATOR_ROLE,ProfileRole.PUBLISHER_ROLE);
        return ResponseEntity.ok(articleService.delete(articleId));
    }

    @GetMapping("/getById")
    public ResponseEntity<?> getById(@RequestParam("id") Integer articleId){
        return ResponseEntity.ok(articleService.getById(articleId));
    }

    @GetMapping("/page")
    public ResponseEntity<?> getAllPaging(@RequestParam("page") int page,
                                          @RequestParam("size") int size,
                                          HttpServletRequest token){
        JwtUtil.checkProfile(token, ProfileRole.MODERATOR_ROLE);
        return ResponseEntity.ok(articleService.list(page,size));
    }

    @GetMapping("/pageFilter")
    public ResponseEntity<?> getAllPagingFilter(@RequestParam("page") int page,
                                          @RequestParam("size") int size,
                                          ArticleFilterDTO dto){
        return ResponseEntity.ok(articleService.filter(page,size,dto));
    }

    @GetMapping("/pageFilterSpec")
    public ResponseEntity<?> pageFilterSpec(@RequestParam("page") int page,
                                                @RequestParam("size") int size,
                                                ArticleFilterDTO dto){
        return ResponseEntity.ok(articleService.filterSpec(page,size,dto));
    }


    @GetMapping("/pageFilterCriteria")
    public void pageFilterCriteria(@RequestParam("page") int page,
                                   @RequestParam("size") int size,
                                   ArticleFilterDTO dto){
        articleService.filterCriteriaBuilder(page,size,dto);
    }

}
