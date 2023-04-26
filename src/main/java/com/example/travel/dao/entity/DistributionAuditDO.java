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
@TableName("distribution_audit")
public class DistributionAuditDO extends Model<DistributionAuditDO> {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 小程序id
     */
    private String openId;

    /**
     * 分销手机号
     */
    private String phone;

    /**
     * 申请备注
     */
    private String remark;

    /**
     * status 状态 1 待审核；2 通过；3 不通过
     */
    private Integer status;

    private Date updateDate;

    private Date createDate;
}
