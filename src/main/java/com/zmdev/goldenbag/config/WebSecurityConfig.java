package com.zmdev.goldenbag.config;


import com.zmdev.fatesdk.spring_insterceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {

    private AuthInterceptor authInterceptor;

    public WebSecurityConfig(@Autowired AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authInterceptor).addPathPatterns("/**")
//                .excludePathPatterns("/login", "/error", "/fate/callback", "/fate/logout");
    }
}