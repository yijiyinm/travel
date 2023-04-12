package com.example.travel.order.dto;

import com.example.travel.product.dto.AddProductDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author yijiyin
 */
@Data
public class SelectOrderDTO {

    /**
     * 订单状态
     * OrderStatusEnum
     */
    private Integer orderStatus;
    /**
     * 支付金额
     */
    private BigDecimal payPrice;
    /**
     * 数量
     */
    private Integer num;
    /**
     * 商品主图地址
     */
    private String mainUrl;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品备注
     */
    private String description;
    /**
     * 订单编码
     */
    private String orderCode;


    /**
     * 详情字段
     */
    /**
     *
     * 产品信息
     */
    private AddProductDTO productInfo;
    /**
     * 游客信息
     */
    private List<TouristDTO> touristInfo;
}
