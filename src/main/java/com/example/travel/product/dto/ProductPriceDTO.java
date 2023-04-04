package com.example.travel.product.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yijiyin
 */
@Data
public class ProductPriceDTO {

    /**
     * 日期到日 字符串
     */
    private String dayDate;

    /**
     * 原价
     */
    private BigDecimal price;

    /**
     * 成人折扣价格
     */
    private BigDecimal priceCr;

    /**
     * 儿童折扣价格
     */
    private BigDecimal priceEt;

}
