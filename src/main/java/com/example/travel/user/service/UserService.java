package com.example.travel.user.service;

import com.example.travel.user.entity.UserDO;

/**
 * @author yijiyin
 */
public interface UserService {
    /**
     * 用户信息查询
     * @param name 昵称
     * @return
     */
    UserDO getUserInfo(String name);

    /**
     * 用户信息查询
     * @param openId 小程序id
     * @return
     */
    UserDO getUserInfoByOpenId(String openId);

    /**
     * 小程序登录code验证
     * @param code 登录凭证code
     * @param name 用户昵称
     * @param phone 用户手机号
     * @return token
     */
    String wxLogin(String code,String name,String phone);
}
