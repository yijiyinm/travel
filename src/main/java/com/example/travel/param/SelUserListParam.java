package com.example.travel.param;

import lombok.Data;

/**
 * @author yijiyin
 */
@Data
public class SelUserListParam {

    private Long size;
    private Long current;


    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户id
     */
    private String openId;

    /**
     * 是否分销商
     */
    private Boolean distributionIs;

    /**
     * 分销商手机号
     */
    private String phone;

    /**
     * 分销商设置天数时间
     */
    private Integer fxsSetDay;
}
