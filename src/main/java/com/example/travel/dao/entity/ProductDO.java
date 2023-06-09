package com.example.travel.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * @author yijiyin
 */
@Data
@TableName("pay_product_info")
public class ProductDO extends Model<ProductDO> {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标签
     */
    private String label;

    /**
     * 月销售
     */
    private Integer monthSalesNum;

    /**
     * 展示顺序
     */
    private Integer sequence;

    /**
     * 主图url
     */
    private String mainUrl;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品详细备注
     */
    private String description;

    /**
     * 提前天数
     */
    private Integer advanceDayNum;

    /**
     * 产品状态 1-草稿，2-上架，3-下架
     */
    private Integer status;

    private Date updateDate;

    private Date createDate;
}
