package com.example.travel.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.travel.product.entity.ProductPriceDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author yijiyin
 */
public interface ProductPriceMapper extends BaseMapper<ProductPriceDO> {

    @Select("select price,price_cr,price_et,day_date from product_price where product_code=#{productCode} and day_date >= #{day} order by day_date ")
    List<ProductPriceDO> getPriceInfoByDay(@Param("productCode") String productCode, @Param("day") String day);
}
