package com.example.travel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author yijiyin
 */
@Data
public class DistributionDTO {

    /**
     * 手机号
     */
    private String phone;


    /**
     * 申请备注
     */
    private String remark;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
     * id
     */
    private Long id;
}
