package com.example.travel.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

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
     * 月份
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Data month;
}
