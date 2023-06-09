package com.example.travel.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.travel.cache.CacheManager;
import com.example.travel.dto.LoginParam;
import com.example.travel.param.SelUserListParam;
import com.example.travel.service.impl.UserService;
import com.example.travel.util.BaseRespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

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
     * 绑定用户分销商
     * @return
     */
    @GetMapping("setUserfxsCode")
    public BaseRespResult setUserfxsCode(HttpServletRequest request,@RequestParam(value = "fxsCode") String fxsCode) {
        String tokeninfo = request.getHeader("tokeninfo");
//        Map<String,String> map = CommenUtils.decryptUserIdAndTokenByStr(tokeninfo);
//        String openId = map.get("openId");

        String openId = CacheManager.get(tokeninfo);
        return BaseRespResult.successResult(userService.setUserfxsCode(openId,fxsCode));
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
     * 作废删除分销商
     */
    @PostMapping("deleteDistribution")
    public BaseRespResult deleteDistribution(@RequestBody SelUserListParam param) {
        userService.deleteDistribution(param.getOpenId());
        return BaseRespResult.successResult("删除作废成功");
    }

    /**
     * 小程序用户获取基本信息
     * @return
     */
    @PostMapping("getUserInfo")
    public BaseRespResult getUserInfo(HttpServletRequest request) {
        // 通过token 获取openId
        // 验证登录
        String tokeninfo = request.getHeader("tokeninfo");

        String openId = CacheManager.get(tokeninfo);
        return BaseRespResult.successResult(userService.getUserInfo(openId));
    }

}
