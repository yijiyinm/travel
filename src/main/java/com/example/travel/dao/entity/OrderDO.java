package com.example.travel.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yijiyin
 */
@Data
@TableName("user_order")
public class OrderDO extends Model<OrderDO> {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单编号
     */
    private String orderCode;

    /**
     * openId
     */
    private String openId;

    /**
     * 产品Code
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 订单金额
     */
    private BigDecimal price;

    /**
     * 购买数量
     */
    private Integer num;

    /**
     * 游客id集合
     */
    private String touristIds;

    /**
     * 是否分销商订单
     */
    private Boolean distributionIs;

    /**
     * 分销商编码
     */
    private String fxsCode;

    /**
     * 分销手机号
     */
    private String fxsPhone;

    /**
     * 商户订单号
     */
    private String outTradeNo;

    /**
     * 出行时间
     */
    private String chuXingDate;

    /**
     * 支付下单返回信息-小程序调起支付用
     */
    private String prePayId;

    /**
     * 总退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 本次退款金额
     */
    private BigDecimal thisRefundAmount;

    /**
     * 退款单号
     */
    private String returnCode;

//    /**
//     * 退款失败原因
//     */
//    private String remark;

    private Date updateDate;

    private Date createDate;






    /**
     * 结算
     */
    private Integer fxsJs;

}
