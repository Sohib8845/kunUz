package com.company.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecuredFilerConfig {

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Bean
    public FilterRegistrationBean<JwtTokenFilter> filterRegistrationBean() {

        FilterRegistrationBean<JwtTokenFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(jwtTokenFilter);
        bean.addUrlPatterns("/article/create");
        bean.addUrlPatterns("/article/update");
        bean.addUrlPatterns("/article/delete");
        bean.addUrlPatterns("/article/page/*");
//        bean.addUrlPatterns("/auth/login");
        bean.addUrlPatterns("/comment/create");
        bean.addUrlPatterns("/profile/createbyAdmin");
        bean.addUrlPatterns("/profile/updateByAdmin");
        bean.addUrlPatterns("/profile/delete-Admin");
        bean.addUrlPatterns("/profile/all-Admin");
        bean.addUrlPatterns("/profile/{id}");

        return bean;
    }

}
