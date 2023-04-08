package com.example.travel.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.travel.product.entity.ProductPriceDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author yijiyin
 */
public interface ProductPriceMapper extends BaseMapper<ProductPriceDO> {

    @Select("select price,price_cr from product_price where product_code=#{productCode} and day_date = #{day}")
    ProductPriceDO getPriceInfoByDay(@Param("productCode") String productCode, @Param("day") String day);
}
