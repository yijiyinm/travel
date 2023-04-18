package com.example.travel.dto;

import lombok.Data;

import java.math.BigDecimal;
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
     * 主图url
     */
    private String mainUrl;
    /**
     * 列表-原价
     */
    private BigDecimal price;
    /**
     * 列表-成人价格（折扣价)
     */
    private BigDecimal priceCr;
    /**
     * 图片地址
     */
    private List<String> urls;

    /**
     * 每日对应价格数据
     */
    private List<ProductPriceDTO> priceDTOS;

    /**
     * 标签
     */
    private String label;

    /**
     * 展示顺序
     */
    private Integer sequence;
}
