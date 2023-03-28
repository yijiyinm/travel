package com.example.travel.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.travel.user.dao.UserMapper;
import com.example.travel.user.entity.UserDO;
import com.example.travel.user.service.UserService;
import com.example.travel.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;


/**
 * @author yijiyin
 */
@Slf4j
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
    @Value("${wx.appid}")
    private static String appId;
    @Value("${wx.appsecret}")
    private static String appSecret;
    @Value("${wx.loginurl}")
    private static String loginUrl;

    @Override
    public UserDO getUserInfo(String name) {
        return getOne(Wrappers.<UserDO>lambdaQuery().eq(UserDO::getName, name));
    }

    @Override
    public String wxLogin(String code) {
        HashMap map = new HashMap();
        map.put("appid",appId);
        map.put("secret",appSecret);
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        String loginReturnString = HttpClientUtil.doGet(loginUrl,map);
        // todo 解析结果
        return null;
    }
}
