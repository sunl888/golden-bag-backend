package com.zmdev.goldenbag.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/test", produces = "application/json;charset=UTF-8")

public class IndexController extends BaseController {

}
