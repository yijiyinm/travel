package com.example.travel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.travel.dao.entity.UserDO;
import com.example.travel.dto.UserDTO;
import com.example.travel.param.SelUserListParam;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author yijiyin
 */
public interface UserMapper extends BaseMapper<UserDO> {
    /**
     * 分销商编码存入
     * @param openId
     * @param fxsCode
     * @return
     */
    @Update("update user_wx set fxs_code = #{fxsCode},fxs_is = 1,phone = #{phone} where open_id=#{openId}")
    void updateFXS(@Param("openId") String openId, @Param("fxsCode") String fxsCode,@Param("phone") String phone);

    /**
     * 用户列表查询
     * @param param
     * @param page
     * @return
     */
    Page<UserDTO> getUserPage(Page<UserDTO> page, @Param("param") SelUserListParam param);

    /**
     * 作废删除分销商
     * @param openId
     * @return
     */
    @Update("update user_wx set fxs_is = 0 where open_id=#{openId}")
    void deleteDistribution(@Param("openId") String openId);

    /**
     * 绑定分销商天数设定
     * @param openId
     * @return
     */
    @Update("update user_wx set fxs_set_day = #{fxsSetDay} where open_id=#{openId}")
    void updateFxsSetDay(@Param("openId") String openId,@Param("fxsSetDay") Integer fxsSetDay);


    /**
     * 分销商编码存入
     * @param openId
     * @param fxsCode
     * @return
     */
    @Update("update user_wx set bind_fxs_code = #{fxsCode},bind_fxs_end_time=date_add(now(), interval #{fxsDay} day) where open_id=#{openId}")
    void setUserfxsCode(@Param("openId") String openId, @Param("fxsCode") String fxsCode,@Param("fxsDay") Integer fxsDay);
}
