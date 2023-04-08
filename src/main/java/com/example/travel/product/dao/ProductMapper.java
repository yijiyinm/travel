package com.example.travel.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.travel.product.entity.ProductDO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author yijiyin
 */
public interface ProductMapper extends BaseMapper<ProductDO> {

    @Select("select label from pay_product_info where status = 2")
    List<String> getProductAllTableWX();
}
