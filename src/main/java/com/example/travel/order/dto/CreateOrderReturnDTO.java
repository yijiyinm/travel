package com.example.travel.order.dto;

import lombok.Data;

/**
 * @author yijiyin
 */
@Data
public class CreateOrderReturnDTO {

    /**
     * 订单编码
     */
    private String orderCode;

    /**
     * 时间戳
     */
    private String timeStamp;

    /**
     * 随机字符串
     */
    private String nonceStr;
    /**
     * 支付id值
     */
    private String packageVal;
    /**
     * appid
     */
    private String appId;

    /**
     * 签名方式
     */
    private String signType;
    /**
     * 签名
     */
    private String paySign;

}
