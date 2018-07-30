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
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PermissionInterceptor extends HandlerInterceptorAdapter {

    private UserService userService;
    private Auth auth;
    private PermissionService permissionService;
    private Pattern permissionNamePattern;
    private static Pattern moduleNamePattern;

    public PermissionInterceptor(UserService userService, Auth auth, PermissionService permissionService) {
        this.userService = userService;
        this.auth = auth;
        this.permissionService = permissionService;
        permissionNamePattern = Pattern.compile("\\.(\\w+)\\.(\\w+)Controller#(\\w+)");
    }

    /**
     * This implementation always returns {@code true}.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (auth.isLogged() && handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String permissionName = convertShortLogMessageToPermissionName(handlerMethod.getShortLogMessage());
            Permission p = permissionService.findByName(permissionName);
            if (!userService.hasPermission(auth.getUser(), p)) {
                throw new PermissionException();
            }
            return true;
        }
        throw new PermissionException();
    }

    public static Map<String, String> baseAbilities = new HashMap<>();

    static {
        moduleNamePattern = Pattern.compile("\\.(\\w+)\\.(\\w+)Controller");
        baseAbilities.put("index", "view");
        baseAbilities.put("edit", "edit");
        baseAbilities.put("show", "view");
        baseAbilities.put("update", "edit");
        baseAbilities.put("create", "add");
        baseAbilities.put("store", "add");
        baseAbilities.put("destroy", "delete");
    }

    public static Map<String, String> specialAbilities = new HashMap<>();

    public static void addSpecialAbilitie(Class classInfo, String methodName, String abilitie) {
        Matcher m = moduleNamePattern.matcher(classInfo.getName());
        if (m.find()) {
            String topModuleName = m.group(1);
            String moduleName = m.group(2);
            specialAbilities.put(topModuleName + "." + moduleName + "." + methodName, abilitie);
        }
    }

    public String getAbilitie(String topModuleName, String moduleName, String methodName) {
        String specialAbilitie = specialAbilities.get(topModuleName + '.' + moduleName + '.' + methodName);
        if (specialAbilitie != null) {
            return specialAbilitie;
        }
        String abilitie = baseAbilities.get(methodName);
        if (abilitie != null) {
            return abilitie;
        }
        return methodName;
    }

    public String convertShortLogMessageToPermissionName(String shortLogMessage) {
        // com.zmdev.goldenbag.web.basic.RoleController#show[1 args]
        Matcher m = permissionNamePattern.matcher(shortLogMessage);
        String topModuleName = "";
        String moduleName = "";
        String methodName = "";
        if (m.find()) {
            topModuleName = m.group(1);
            moduleName = m.group(2);
            if (moduleName.length() <= 1) {
                moduleName = moduleName.toLowerCase();
            } else {
                moduleName = moduleName.substring(0, 1).toLowerCase() + moduleName.substring(1);
            }
            methodName = m.group(3);
        }
        return topModuleName + "." + moduleName + "." + getAbilitie(topModuleName, moduleName, methodName);
    }
}
