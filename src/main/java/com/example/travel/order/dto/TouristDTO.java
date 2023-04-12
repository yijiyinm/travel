package com.example.travel.order.dto;

import lombok.Data;

/**
 * @author yijiyin
 */
@Data
public class TouristDTO {
    /**
     * 游客信息id
     */
    private Long id;
    /**
     * 游客姓名
     */
    private String name;
    /**
     * 身份证
     */
    private String cardId;
    /**
     * 手机号
     */
    private String phone;
}
