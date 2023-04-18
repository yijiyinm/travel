package com.example.travel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.travel.dao.entity.ProductDO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author yijiyin
 */
public interface ProductMapper extends BaseMapper<ProductDO> {

    @Select("select label from pay_product_info where status = 2 group by label")
    List<String> getProductAllTableWX();
}
