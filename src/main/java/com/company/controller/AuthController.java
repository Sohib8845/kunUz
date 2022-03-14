package com.company.controller;

import com.company.dto.profile.ProfileDTO;
import com.company.dto.RegistrationDTO;
import com.company.dto.auth.AuthorizationDTO;
import com.company.util.JwtUtil;
import com.company.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Api(tags = "Auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @ApiOperation(value = "Login method", notes="Sekinroq ishlami qolishi mn ", nickname = "nikname")
    public ResponseEntity<ProfileDTO> login(@RequestBody AuthorizationDTO dto) {
        ProfileDTO response = authService.authorization(dto);
        System.out.println("-------------------------DTO--------------------------------");
        System.out.println(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registration")
    @ApiOperation(value = "Registration method")
    public  ResponseEntity<RegistrationDTO> registration(@Valid @RequestBody RegistrationDTO dto){
        System.out.println("-------------------------DTO--------------------------------");
        authService.registration(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/verification/{jwt}")
    @ApiOperation(value = "Verification method")
    public  ResponseEntity<RegistrationDTO> verification(@PathVariable("jwt") String jwt){
        System.out.println("-------------------------JWT--------------------------------");
        authService.verification(jwt);
        return ResponseEntity.ok().build();
    }
}
