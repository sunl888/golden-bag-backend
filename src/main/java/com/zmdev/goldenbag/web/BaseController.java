package com.zmdev.goldenbag.web;

import com.zmdev.goldenbag.domain.User;
import com.zmdev.goldenbag.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.zmdev.fatesdk.Util;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BaseController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 返回当前登录的用户id 如果没有登录返回0
     *
     * @return
     */
    protected long getUserId() {
        HttpServletRequest request = getRequest();
        return Util.userId(request);
    }

    /**
     * 获取当前登录的用户
     * return null if not logged
     *
     * @return User|null
     */
    protected User getUser() {
        HttpServletRequest request = getRequest();
        Object userObj = request.getAttribute("user");

        if (userObj == null) {
            if (Util.isLogin(request)) {
                userObj = userService.findById(Util.userId(request)).orElse(null);
                if (userObj != null) {
                    request.setAttribute("user", userObj);
                } else {
                    return null;
                }
            }
        }
        return (User) userObj;
    }
}
