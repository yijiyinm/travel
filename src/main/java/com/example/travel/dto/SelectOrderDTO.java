package com.example.travel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
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
     * 分销商编码
     */
    private String fxsCode;

    /**
     * 分销手机号
     */
    private String fxsPhone;

    /**
     * 出行时间
     */
    private String chuXingDate;

    /**
     * 订单创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;


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
