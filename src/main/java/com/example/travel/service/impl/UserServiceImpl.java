package com.example.travel.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.travel.cache.CacheManager;
import com.example.travel.dao.UserMapper;
import com.example.travel.dao.entity.OrderDO;
import com.example.travel.dao.entity.UserDO;
import com.example.travel.dto.UserDTO;
import com.example.travel.param.SelUserListParam;
import com.example.travel.util.AppInfoEnum;
import com.example.travel.util.GenerateCodeUtil;
import com.example.travel.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author yijiyin
 */
@Slf4j
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
    private static String loginUrl = "https://api.weixin.qq.com/sns/jscode2session";


    @Override
    public Page<UserDTO> getUserPage(SelUserListParam param) {
        Page<UserDTO> pageDTO = new Page(param.getCurrent(),param.getSize());

        Page<UserDO> page = new Page(param.getCurrent(),param.getSize());
        LambdaQueryWrapper<UserDO> wrapper = Wrappers.<UserDO>lambdaQuery()
                .like(StringUtils.isNotEmpty(param.getPhone()),UserDO::getPhone,param.getPhone());

        if (param.getDistributionIs() != null){
            if (param.getDistributionIs()){
                // 是供应商
                wrapper.isNotNull(UserDO::getFxsCode);
            }else {
                wrapper.isNull(UserDO::getFxsCode);
            }
        }
        Page<UserDO> doPage = page(page,wrapper);
        log.info("用户总数：{}",doPage.getTotal());
        pageDTO.setTotal(doPage.getTotal());
        List<UserDTO> userDTOS = new ArrayList<>();
        for (UserDO userDO :doPage.getRecords()){
            UserDTO userDTO = new UserDTO();
            userDTO.setFxsCode(userDO.getFxsCode());
            userDTO.setName(userDO.getName());
            userDTO.setOpenId(userDO.getOpenId());
            userDTO.setPhone(userDO.getPhone());
            userDTO.setCreateDate(userDO.getCreateDate());
            userDTOS.add(userDTO);
        }
        pageDTO.setRecords(userDTOS);


        return pageDTO;
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
        log.info("登录信息：{}",userName);
        // 验证后台用户名密码 返回token
        UserDO userDO = getOne(Wrappers.<UserDO>lambdaQuery().eq(UserDO::getPhone, userName));
        if (userDO != null) {
            if (userPassword.equals(userDO.getPassword())) {
                String token = GenerateCodeUtil.createCode(20);
                CacheManager.put(token,userDO.getOpenId());
                return token;
            }
            return null;
        }
        return null;
    }

    @Override
    public void updateFXS(String openId, String fxsCode,String phone) {
        baseMapper.updateFXS(openId,fxsCode,phone);
    }

    @Override
    public UserDTO getUserInfo(String openId) {
        UserDTO userDTO = new UserDTO();
        UserDO userDO = getOne(Wrappers.<UserDO>lambdaQuery().eq(UserDO::getOpenId, openId));
        if (StringUtils.isNotEmpty(userDO.getFxsCode())){
            userDTO.setFxsCode(userDO.getFxsCode());
            return userDTO;
        }
        return null;
    }

    @Override
    public UserDO getUserInfoByOpenId(String openId){
        log.info("getUserInfoByOpenId openId：{}",openId);

        UserDO one = getOne(Wrappers.<UserDO>lambdaQuery().eq(UserDO::getOpenId, openId));
        log.info("one {}",JSON.toJSONString(one));
        return one;
    }
}
