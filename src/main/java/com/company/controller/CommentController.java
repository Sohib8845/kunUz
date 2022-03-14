package com.company.controller;

import com.company.dto.article.ArticleFilterDTO;
import com.company.dto.comment.CommentDTO;
import com.company.dto.comment.CommentFilterDTO;
import com.company.dto.profile.ProfileJwtDTO;
import com.company.enums.ProfileRole;
import com.company.service.CommentService;
import com.company.service.ProfileService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<CommentDTO> createProfile(@RequestBody CommentDTO dto,
                                                    @RequestParam("id") Integer articleId,
                                                    HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = JwtUtil.checkProfile(request, ProfileRole.USER_ROLE,ProfileRole.MODERATOR_ROLE);
        CommentDTO result = commentService.create(dto,jwtDTO.getId(),articleId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateByAdmin(@RequestParam("id") Integer commentId,
                                           @RequestBody CommentDTO dto,
                                           HttpServletRequest request){
        ProfileJwtDTO jwtDTO = JwtUtil.checkProfile(request);
        return  ResponseEntity.ok(commentService.update(commentId,dto,jwtDTO.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBbyID(@PathVariable("id") Integer id, HttpServletRequest request ){
        JwtUtil.checkProfile(request, ProfileRole.USER_ROLE,
                ProfileRole.MODERATOR_ROLE);
        return ResponseEntity.ok(commentService.getByID(id));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteByAdmin(@RequestParam("Id") Integer id,HttpServletRequest request){
        ProfileJwtDTO jwtDTO = JwtUtil.checkProfile(request, ProfileRole.USER_ROLE);
        return ResponseEntity.ok(commentService.delete(id,jwtDTO.getId()));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(HttpServletRequest request){
        JwtUtil.checkProfile(request, ProfileRole.ADMIN_ROLE);
        List<CommentDTO> dtoList=commentService.getList();
        if (dtoList.isEmpty()) {
            return ResponseEntity.badRequest().body("Empty");
        }else return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/pageFilter")
    public ResponseEntity<?> pageFilter(@RequestParam int page,
                                        @RequestParam int size,
                                        CommentFilterDTO dto){
        return ResponseEntity.ok(commentService.filter(page,size,dto));
    }
}
