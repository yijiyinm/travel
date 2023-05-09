package com.example.travel.controller;

import com.example.travel.dao.entity.SysKeyDO;
import com.example.travel.service.SysKeyService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SysKeyService sysKeyService;

    @RequestMapping("/")
    public String view(Model model) {
        SysKeyDO js_version = sysKeyService.getInfoByKey("js_version");
        model.addAttribute("js_version",js_version.getKeyValue());
        SysKeyDO css_version = sysKeyService.getInfoByKey("css_version");
        model.addAttribute("css_version",css_version.getKeyValue());
        return "index";
    }
    @RequestMapping("/index")
    public String index(Model model) {
        SysKeyDO js_version = sysKeyService.getInfoByKey("js_version");
        model.addAttribute("js_version",js_version.getKeyValue());
        SysKeyDO css_version = sysKeyService.getInfoByKey("css_version");
        model.addAttribute("css_version",css_version.getKeyValue());
        return "index";
    }

}
