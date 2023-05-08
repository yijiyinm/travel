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
    @Update("update user_wx set fxs_code = #{fxsCode} where open_id=#{openId}")
    void updateFXS(@Param("openId") String openId, @Param("fxsCode") String fxsCode);

    /**
     * 用户列表查询
     * @param param
     * @param page
     * @return
     */
    Page<UserDTO> getUserPage(Page<UserDTO> page, @Param("param") SelUserListParam param);
}
