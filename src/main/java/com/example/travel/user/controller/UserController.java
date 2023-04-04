package com.example.travel.user.controller;

import com.example.travel.user.entity.UserDO;
import com.example.travel.user.service.UserService;
import com.example.travel.util.BaseRespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yijiyin
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * 查询用户测试
     * @return
     */
    @GetMapping("getUser")
    public UserDO getUser(@RequestParam(value = "name") String name) {
        return userService.getUserInfo(name);
    }

    /**
     * 小程序用户登录
     * @return
     */
    @PostMapping("wxLogin")
    public BaseRespResult wxLogin(@RequestParam(value = "code") String code) {
        return BaseRespResult.successResult(userService.wxLogin(code,null,null));
    }

    /**
     * 微信小程序code验证登录
     */

}
