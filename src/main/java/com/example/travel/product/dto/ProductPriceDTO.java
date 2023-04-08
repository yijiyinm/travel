package com.example.travel.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yijiyin
 */
@Data
public class ProductPriceDTO {

    /**
     * 日期到日 字符串
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dayDate;

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
