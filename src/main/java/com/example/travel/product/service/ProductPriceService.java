package com.example.travel.product.service;

import com.example.travel.product.entity.ProductPriceDO;

import java.util.List;

/**
 * @author yijiyin
 */
public interface ProductPriceService {
    /**
     * 根据产品code 查询当天及之后的价格信息
     * @param productCode
     * @return
     */
    List<ProductPriceDO> getPriceInfoByDay(String productCode,String day);

}
