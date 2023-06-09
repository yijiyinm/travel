package com.example.travel.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author yijiyin
 */
@Data
public class UserDTO {

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
     * 分销商编码
     */
    private String fxsCode;

    /**
     * 分销商设置天数时间
     */
    private Integer fxsSetDay;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 创建时间
     */
    private Date createDate;
}
