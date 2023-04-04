package com.example.travel.product.dto;

import lombok.Data;

import java.util.List;


/**
 * @author yijiyin
 */
@Data
public class AddProductDTO {

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 备注详细描述
     */
    private String description;

    /**
     * 产品状态 1-草稿，2-上架，3-下架
     */
    private Integer status;
    /**
     * 图片地址
     */
    private List<String> urls;

    /**
     * 每日对应价格数据
     */
    private List<ProductPriceDTO> priceDTOS;

}
