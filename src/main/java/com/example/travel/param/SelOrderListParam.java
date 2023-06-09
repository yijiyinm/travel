package com.example.travel.param;

import lombok.Data;

import java.math.BigDecimal;


/**
 * @author yijiyin
 */
@Data
public class SelOrderListParam {

    private Long size;
    private Long current;

    /**
     * 订单编码
     */
    private String orderCode;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * openId
     */
    private String openId;

    /**
     * 订单状态
     * OrderStatusEnum
     */
    private Integer orderStatus;

    /**
     * 是否分销商订单
     */
    private Boolean distributionIs;

    /**
     * 分销商编码
     */
    private String fxsCode;

    /**
     * 分销手机号
     */
    private String fxsPhone;

    /**
     * 创建时间月份
     */
    private String month;


    /**
     * 订单编码List
     */
    private String orderCodes;


    /**
     * 结算
     */
    private Integer fxsJs;
}
