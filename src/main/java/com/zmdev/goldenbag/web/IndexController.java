package com.zmdev.goldenbag.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/test", produces = "application/json;charset=UTF-8")

public class IndexController extends BaseController {
    @GetMapping(value = "/index")
    @ResponseBody
    public String index(HttpServletRequest request) {

        return "Hello World!" + getUserId();
    }
}
