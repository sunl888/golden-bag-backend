package com.zmdev.goldenbag.web.insterceptor;

import com.zmdev.goldenbag.domain.Permission;
import com.zmdev.goldenbag.exception.PermissionException;
import com.zmdev.goldenbag.service.PermissionService;
import com.zmdev.goldenbag.service.UserService;
import com.zmdev.goldenbag.web.Auth;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PermissionInterceptor extends HandlerInterceptorAdapter {

    private UserService userService;
    private Auth auth;
    private PermissionService permissionService;

    public PermissionInterceptor(UserService userService, Auth auth, PermissionService permissionService) {
        this.userService = userService;
        this.auth = auth;
        this.permissionService = permissionService;
    }

    /**
     * This implementation always returns {@code true}.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (!auth.isLogged() && handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String shortLogMessage = handlerMethod.getShortLogMessage();
            Permission p = permissionService.findByShortLogMessage(shortLogMessage);
            if (!userService.hasPermission(auth.getUser(), p)) {
                throw new PermissionException();
            }
            return true;
        }
        throw new PermissionException();
    }
}
