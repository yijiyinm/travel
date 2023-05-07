package com.example.travel.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.travel.cache.CacheManager;
import com.example.travel.dao.UserMapper;
import com.example.travel.dao.entity.UserDO;
import com.example.travel.dto.UserDTO;
import com.example.travel.param.SelUserListParam;
import com.example.travel.util.AppInfoEnum;
import com.example.travel.util.GenerateCodeUtil;
import com.example.travel.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;


/**
 * @author yijiyin
 */
@Slf4j
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
    private static String loginUrl = "https://api.weixin.qq.com/sns/jscode2session";


    @Override
    public Page<UserDTO> getUserPage(SelUserListParam param) {
        Page<UserDTO> page = new Page(param.getCurrent(),param.getSize());
        return this.baseMapper.getUserPage(page,param);
    }

    @Override
    public String wxLogin(String code,String name,String phone) {
        log.info("登录信息code:{}",code);
        HashMap map = new HashMap(4);
        map.put("appid",AppInfoEnum.APP_ID.getValue());
        map.put("secret", AppInfoEnum.APP_SECRET.getValue());
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
        // 将生成的token编码和openId,sessionKey关联存储 session,全局Map或redis
        CacheManager.put(token,openId);
        // 返回给前端
        return token;
    }

    @Override
    public String login(String userName, String userPassword) {
        // todo 验证后台用户名密码 返回token

        //loginCache.put(token,openId);
        return null;
    }

    @Override
    public void updateFXS(String openId, String fxsCode) {
        baseMapper.updateFXS(openId,fxsCode);
    }

    @Override
    public UserDTO getUserInfo(String openId) {
        UserDTO userDTO = new UserDTO();
        UserDO userDO = getOne(Wrappers.<UserDO>lambdaQuery().eq(UserDO::getOpenId, openId));
        if (StringUtils.isNotEmpty(userDO.getFxsCode())){
            userDTO.setFxsCode(userDO.getFxsCode());
        }
        return null;
    }

    @Override
    public UserDO getUserInfoByOpenId(String openId){
        return getOne(Wrappers.<UserDO>lambdaQuery().eq(UserDO::getOpenId, openId));
    }
}
