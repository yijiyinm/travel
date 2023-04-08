package com.example.travel.product.service;

import com.example.travel.product.entity.ProductPriceDO;

import java.util.List;

/**
 * @author yijiyin
 */
public interface ProductPriceService {
    /**
     * 根据产品编码获取所有价格信息
     * @param productCode
     * @return
     */
    List<ProductPriceDO> getPriceInfoByProductCode(String productCode);

    /**
     * 根据产品code 查询当天价格信息
     * @param productCode
     * @return
     */
    ProductPriceDO getPriceInfoByDay(String productCode,String day);

}
