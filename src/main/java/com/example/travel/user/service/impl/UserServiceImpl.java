package com.example.travel.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.travel.user.dao.UserMapper;
import com.example.travel.user.entity.UserDO;
import com.example.travel.user.service.UserService;
import com.example.travel.util.GenerateCodeUtil;
import com.example.travel.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;


/**
 * @author yijiyin
 */
@Slf4j
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
    private static String appId = "wx2e0c5d458b1896b5";
    private static String appSecret ="6ead95f2fcd40471225aaa9083528914";
    private static String loginUrl = "https://api.weixin.qq.com/sns/jscode2session";

    @Override
    public UserDO getUserInfo(String name) {
        return getOne(Wrappers.<UserDO>lambdaQuery().eq(UserDO::getName, name));
    }

    @Override
    public String wxLogin(String code,String name,String phone) {
        log.info("登录信息code:{}",code);
        HashMap map = new HashMap(4);
        map.put("appid",appId);
        map.put("secret",appSecret);
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        String loginReturnString = HttpClientUtil.doGet(loginUrl,map);
        log.info("返回数据：{}",loginReturnString);
        JSONObject jsonObject = JSON.parseObject(loginReturnString);
        log.info("返回数据：{}",jsonObject);

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
        String token = GenerateCodeUtil.createCode(20);
        log.info("生成的token:{}",token);
        // todo 将生成的token编码和openId,sessionKey关联存储 session,全局Map或redis

        // 返回给前端
        return token;
    }

    @Override
    public UserDO getUserInfoByOpenId(String openId){
        return getOne(Wrappers.<UserDO>lambdaQuery().eq(UserDO::getOpenId, openId));
    }
}
