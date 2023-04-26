package com.example.travel.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yijiyin
 * @date 2023/03/28 13:31
 */
@Data
@TableName("user")
public class UserDO extends Model<UserDO> {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 昵称
     */
    private String name;
    /**
     * 手机号
     */
    private String phone;
    /**
     * openId
     */
    private String openId;

    /**
     * unionId
     */
    private String unionId;

    /**
     * 分销商编码
     */
    private String fxsCode;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 创建时间
     */
    private Date createDate;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}