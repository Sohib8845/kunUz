package com.company.controller;

import com.company.dto.RegionDTO;
import com.company.enums.ProfileRole;
import com.company.service.RegionService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody RegionDTO dto,
                                    HttpServletRequest token){
        JwtUtil.checkProfile(token, ProfileRole.ADMIN_ROLE);
        RegionDTO responce = regionService.create(dto);
        return ResponseEntity.ok(responce);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(HttpServletRequest token,
                                    @RequestBody RegionDTO dto,
                                    @RequestParam("id") Integer articleId){
        JwtUtil.checkProfile(token, ProfileRole.ADMIN_ROLE);
        return ResponseEntity.ok(regionService.update(dto,articleId));
    }

    @PutMapping("/delete")
    public ResponseEntity<?> delete(HttpServletRequest token,
                                    @RequestParam("id") Integer articleId){
        JwtUtil.checkProfile(token, ProfileRole.ADMIN_ROLE);
        return ResponseEntity.ok(regionService.delete(articleId));
    }

    @GetMapping("/getById")
    public ResponseEntity<?> getById(@RequestParam("id") Integer articleId){
        return ResponseEntity.ok(regionService.getById(articleId));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(){
        return ResponseEntity.ok(regionService.list());
    }


}
