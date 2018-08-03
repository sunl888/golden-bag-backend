package com.zmdev.goldenbag.config;


import com.zmdev.fatesdk.spring_insterceptor.AuthInterceptor;
import com.zmdev.goldenbag.service.PermissionService;
import com.zmdev.goldenbag.service.UserService;
import com.zmdev.goldenbag.web.Auth;
import com.zmdev.goldenbag.web.insterceptor.PermissionInterceptor;
import com.zmdev.goldenbag.web.insterceptor.TemplateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {

    private AuthInterceptor authInterceptor;

    private TemplateInterceptor templateInterceptor;

    private PermissionService permissionService;

    private Auth auth;

    private UserService userService;

    @Autowired
    public WebSecurityConfig(AuthInterceptor authInterceptor, PermissionService permissionService, Auth auth, UserService userService, TemplateInterceptor templateInterceptor) {
        this.authInterceptor = authInterceptor;
        this.permissionService = permissionService;
        this.auth = auth;
        this.userService = userService;
        this.templateInterceptor = templateInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 判断登录拦截器
        registry.addInterceptor(authInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/", "/auth/*", "/error", "/fate/callback", "/fate/logout");
        // 权限验证拦截器
        registry.addInterceptor(new PermissionInterceptor(userService, auth, permissionService)).addPathPatterns("/**")
                // 单个导出考核记录接口不做权限验证 在此接口内部用代码做验证
                .excludePathPatterns("assessments/export/*", "templates/types", "permissions/menus", "templates/get_template", "/setup/*", "/error", "/login", "/error", "/fate/callback", "/fate/logout");
        // 模板编辑拦截器
        registry.addInterceptor(templateInterceptor).addPathPatterns("/templates/**");
    }
}
