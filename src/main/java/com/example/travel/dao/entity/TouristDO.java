package com.example.travel.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * @author yijiyin
 */
@Data
@TableName("tourist_info")
public class TouristDO extends Model<TouristDO> {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 所属用户openId
     */
    private String openId;

    /**
     * 游客姓名
     */
    private String name;

    /**
     * 身份证号
     */
    private String cardId;

    /**
     * 手机号
     */
    private String phone;


    private Date updateDate;

    private Date createDate;
}
