package com.company.controller;

import com.company.dto.profile.ProfileDTO;
import com.company.enums.ProfileRole;
import com.company.service.ProfileService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/create")
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileDTO dto) {
        ProfileDTO response = profileService.create(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/createbyAdmin")
    public ResponseEntity<ProfileDTO> createProfile(@RequestBody ProfileDTO dto, HttpServletRequest request) {
        JwtUtil.checkProfile(request, ProfileRole.ADMIN_ROLE);
        ProfileDTO result = profileService.create(dto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/updateByAdmin")
    public ResponseEntity<?> updateByAdmin(@RequestParam("id") Integer pid,
                                           @RequestBody ProfileDTO dto,
                                           HttpServletRequest request){
        JwtUtil.checkProfile(request, ProfileRole.ADMIN_ROLE);
        boolean result=profileService.update(dto,pid);
        if (result) return  ResponseEntity.ok("Update");
        else return ResponseEntity.badRequest().body("Not Update");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBbyID(@PathVariable("id") Integer id, HttpServletRequest request ){
        ProfileDTO dto;
        JwtUtil.checkProfile(request, ProfileRole.ADMIN_ROLE);
        try {
             dto = profileService.getByID(id);
        }catch (RuntimeException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/deleteByAdmin")
    public ResponseEntity<?> deleteByAdmin(@RequestParam("Id") Integer id,HttpServletRequest request){
        JwtUtil.checkProfile(request, ProfileRole.ADMIN_ROLE);
        ProfileDTO dto=profileService.deleteByAdmin(id);
        if (dto==null) return ResponseEntity.badRequest().body("mazgi");
        else return ResponseEntity.ok(dto);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(HttpServletRequest request){
        JwtUtil.checkProfile(request, ProfileRole.ADMIN_ROLE);
        List<ProfileDTO> dtoList=profileService.getList();
        if (dtoList.isEmpty()) {
            return ResponseEntity.badRequest().body("Empty");
        }else return ResponseEntity.ok(dtoList);
    }


    // registration
    // authorization
}
