package com.example.travel.controller;

import com.example.travel.dto.LoginParam;
import com.example.travel.param.SelUserListParam;
import com.example.travel.service.impl.UserService;
import com.example.travel.util.BaseRespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yijiyin
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 小程序用户登录
     * @return
     */
    @GetMapping("wxLogin")
    public BaseRespResult wxLogin(@RequestParam(value = "code") String code) {
        return BaseRespResult.successResult(userService.wxLogin(code,null,null));
    }

    /**
     * 后台用户登录
     * @return
     */
    @PostMapping("login")
    public BaseRespResult login(@RequestBody LoginParam loginParam) {
        return BaseRespResult.successResult(userService.login(loginParam.getUsername(),loginParam.getPassword()));
    }


    /**
     * 后台查询用户列表
     * @return
     */
    @PostMapping("getUserList")
    public BaseRespResult getUserList(@RequestBody SelUserListParam param) {
        return BaseRespResult.successResult(userService.getUserPage(param));
    }

    /**
     * 小程序用户获取基本信息
     * @return
     */
    @PostMapping("getUserInfo")
    public BaseRespResult getUserInfo(@RequestBody SelUserListParam param) {
        // todo 通过token 获取openId
        return BaseRespResult.successResult(userService.getUserInfo(""));
    }

}
