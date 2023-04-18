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
@TableName("product_price")
public class ProductPriceDO extends Model<ProductPriceDO> {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 原价
     */
    private BigDecimal price;

    /**
     * 成人价格
     */
    private BigDecimal priceCr;

    /**
     * 儿童价格
     */
    private BigDecimal priceEt;

    /**
     * 价格日期
     */
    private Date dayDate;
}
