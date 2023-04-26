package com.example.travel.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.travel.dao.entity.UserDO;
import com.example.travel.dto.UserDTO;
import com.example.travel.param.SelUserListParam;

/**
 * @author yijiyin
 */
public interface UserService {
    /**
     * 用户信息查询
     * @param name 昵称
     * @return
     */
    Page<UserDTO> getUserInfo(SelUserListParam param);

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

    /**
     * 后台用户登录
     * @param userName 用户昵称
     * @param userPassword 用户手机号
     * @return token
     */
    String login(String userName,String userPassword);

    /**
     * 分销商编码存入
     * @param openId
     * @param fxsCode
     * @return
     */
    void updateFXS(String openId,String fxsCode);
}
