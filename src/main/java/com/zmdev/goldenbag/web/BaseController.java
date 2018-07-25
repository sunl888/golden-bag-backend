package com.zmdev.goldenbag.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BaseController {
}

