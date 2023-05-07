package com.example.travel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chenying
 * @Description TODO
 * @Date 2023/5/6 18:18
 */
@Controller
public class WelcomeController {

    @RequestMapping("/")
    public String view(Model model) {
        model.addAttribute("js_version","index-96139d1d");
        model.addAttribute("css_version","index-1183b5a2");
        return "index";
    }

}
