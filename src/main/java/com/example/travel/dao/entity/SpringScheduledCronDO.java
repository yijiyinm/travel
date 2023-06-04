package com.example.travel.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @author chenying
 * @Description TODO
 * @Date 2023/5/29 14:09
 */
@Data
@TableName("spring_scheduled_cron")
public class SpringScheduledCronDO extends Model<SpringScheduledCronDO> {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer cronId;

    private String cronKey;

    private String cronExpression;

    private String taskExplain;

    private Integer status;
}
