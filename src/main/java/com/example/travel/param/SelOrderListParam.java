package com.example.travel.param;

import lombok.Data;


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
     * 创建时间月份
     */
    private String month;
}
