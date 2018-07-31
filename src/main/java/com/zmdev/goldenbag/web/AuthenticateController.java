package com.zmdev.goldenbag.web;

import com.zmdev.fatesdk.Fate;
import com.zmdev.fatesdk.FateConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("auth")
public class AuthenticateController extends BaseController {

    private Fate fate;
    private Auth auth;
    private FateConfiguration fateConfiguration;

    @Autowired
    public void setFate(Fate fate) {
        this.fate = fate;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    @Autowired
    public void setFateConfiguration(FateConfiguration fateConfiguration) {
        this.fateConfiguration = fateConfiguration;
    }

    @GetMapping("/login")
    public String login(@RequestParam(defaultValue = "/") String callback, HttpServletResponse response) {
        if (auth.isLogged()) {
            return "redirect:" + callback;
        }
        try {
            fate.redirectToLoginWithCallback(callback, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:" + fateConfiguration.getFateURL() + "/logout?app_id=" + fateConfiguration.getAppId();
    }
}
