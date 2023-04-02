package com.example.travel.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.travel.user.dao.UserMapper;
import com.example.travel.user.entity.UserDO;
import com.example.travel.user.service.UserService;
import com.example.travel.util.GenerateTokenCodeUtil;
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
    public String wxLogin(String code,String name,String phone) {
        HashMap map = new HashMap(4);
        map.put("appid",appId);
        map.put("secret",appSecret);
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        String loginReturnString = HttpClientUtil.doGet(loginUrl,map);
        JSONObject jsonObject = JSON.parseObject(loginReturnString);

        // 小程序用户数据
        String openId = jsonObject.getString("openid");
        String unionId = jsonObject.getString("unionId");
        String sessionKey = jsonObject.getString("session_key");

        // 通过openId查询验证用户是否存在
        UserDO user = getUserInfoByOpenId(openId);
        if (user == null) {
            user = new UserDO();
            user.setOpenId(openId);
            user.setUnionId(unionId);
            user.setName(name);
            user.setPhone(phone);
            user.insert();
        }
        // 生成以sessionKey和token为对应的token编码
        String token = GenerateTokenCodeUtil.createTokenCode();
        // todo 将生成的token编码和openId,sessionKey关联存储 session,全局Map或redis

        // 返回给前端
        return token;
    }

    @Override
    public UserDO getUserInfoByOpenId(String openId){
        return getOne(Wrappers.<UserDO>lambdaQuery().eq(UserDO::getOpenId, openId));
    }
}
