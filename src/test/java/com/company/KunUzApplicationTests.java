package com.company;

import com.company.dto.article.ArticleDTO;
import com.company.dto.RegistrationDTO;
import com.company.service.ArticleService;
import com.company.service.AuthService;
import com.company.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KunUzApplicationTests {
    @Autowired
    ProfileService profileService;
    @Autowired
    ArticleService articleService;
    @Autowired
    AuthService authService;

    @Test
    void createProfile() {
        RegistrationDTO dto=new RegistrationDTO();
        dto.setName("Admin");
        dto.setSurname("Admin");
        dto.setLogin("admin");
        dto.setEmail("admin@gmail.com");
        dto.setPswd("123");
        authService.registration(dto);
    }
    @Test
    void createArticle(){
        ArticleDTO dto=new ArticleDTO();
        dto.setTitle("Dollor kursi");
        dto.setContent("Dollor kursi pasaymoqda");

    }

    @Test
    void filterTest(){
        ArticleDTO dto=new ArticleDTO();
        dto.setTitle("Dollor kursi");
        dto.setContent("Dollor kursi pasaymoqda");
    }
}
