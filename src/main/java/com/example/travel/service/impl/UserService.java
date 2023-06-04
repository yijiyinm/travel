package com.example.travel.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.travel.dao.entity.UserDO;
import com.example.travel.dto.UserDTO;
import com.example.travel.dto.WxUserDTO;
import com.example.travel.param.SelUserListParam;

/**
 * @author yijiyin
 */
public interface UserService {
    /**
     * 用户信息列表查询
     * @param param 昵称
     * @return
     */
    Page<UserDTO> getUserPage(SelUserListParam param);

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
     * @param phone
     * @return
     */
    void updateFXS(String openId,String fxsCode,String phone);

    /**
     * 成为分销商
     * @param userDO
     * @return
     */
    void updateByIds(UserDO userDO);

    /**
     * 小程序用户获取基本信息
     * @param  openId
     * @return
     */
    WxUserDTO getUserInfo(String openId);

    /**
     * 根据分销商编码获取信息
     * @param  fxsCode
     * @return
     */
    UserDO getUserInfoByFxsCode(String fxsCode);

    /**
     * 作废删除分销商
     * @param openId 小程序id
     * @return
     */
    boolean deleteDistribution(String openId);
}
