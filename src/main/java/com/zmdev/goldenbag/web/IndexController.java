package com.zmdev.goldenbag.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController extends BaseController {
    @GetMapping("/")
    public String index() {
        return "/index.html";
    }
}
