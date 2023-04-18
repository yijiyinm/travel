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
@TableName("product_img")
public class ProductImgDO extends Model<ProductImgDO> {
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
     * 图片地址
     */
    private String imgUrl;

    private Date updateDate;

    private Date createDate;

}
