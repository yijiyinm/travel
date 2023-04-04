package com.example.travel.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.travel.product.entity.ProductImgDO;

/**
 * @author yijiyin
 */
public interface ProductImgMapper extends BaseMapper<ProductImgDO> {

    /**
     * 清空
     * @param productCode
     */
    void deleteInfoByCode(String productCode);
}
