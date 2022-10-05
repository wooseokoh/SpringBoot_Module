package com.oos.cloud.standard.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BaseController {

    @GetMapping
    public String index(String data, ModelMap modelMap){
        modelMap.addAttribute("data", "get " + data);
        return "index";
    }

    @PostMapping
    public String postIndex(String data, ModelMap modelMap){
        modelMap.addAttribute("data", "post " + data);
        return "index";
    }
}
