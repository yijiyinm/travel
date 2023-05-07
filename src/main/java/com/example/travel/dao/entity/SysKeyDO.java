package com.example.travel.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @author yijiyin
 */
@Data
@TableName("sys_key")
public class SysKeyDO extends Model<SysKeyDO> {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    private String keyName;

    private String keyValue;
}
