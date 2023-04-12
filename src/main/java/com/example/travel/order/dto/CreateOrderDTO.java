package com.example.travel.order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author yijiyin
 */
@Data
public class CreateOrderDTO {

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;


    /**
     * 成人票数量
     */
    private Integer crNum;


    /**
     * 儿童票数量
     */
    private Integer etNum;


    /**
     * 订单金额
     */
    private BigDecimal payPrice;


    /**
     * 游客信息
     */
    private List<Integer> touristIds;
}
