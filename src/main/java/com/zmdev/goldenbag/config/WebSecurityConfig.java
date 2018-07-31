package com.zmdev.goldenbag.config;


import com.zmdev.fatesdk.spring_insterceptor.AuthInterceptor;
import com.zmdev.goldenbag.service.PermissionService;
import com.zmdev.goldenbag.service.UserService;
import com.zmdev.goldenbag.web.Auth;
import com.zmdev.goldenbag.web.insterceptor.PermissionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {

    private AuthInterceptor authInterceptor;

    private PermissionService permissionService;

    private Auth auth;

    private UserService userService;

    @Autowired
    public WebSecurityConfig(AuthInterceptor authInterceptor, PermissionService permissionService, Auth auth, UserService userService) {
        this.authInterceptor = authInterceptor;
        this.permissionService = permissionService;
        this.auth = auth;
        this.userService = userService;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 判断登录拦截器
        registry.addInterceptor(authInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/login", "/error", "/fate/callback", "/fate/logout");
        // 权限验证拦截器
//        registry.addInterceptor(new PermissionInterceptor(userService, auth, permissionService)).addPathPatterns("/**")
//                .excludePathPatterns("/test/*", "/error", "/login", "/error", "/fate/callback", "/fate/logout");
    }
}
