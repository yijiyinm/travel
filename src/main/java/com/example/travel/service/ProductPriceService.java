package com.example.travel.service;

import com.example.travel.dao.entity.ProductPriceDO;
import com.example.travel.dto.ProductPriceDTO;

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

    /**
     * 根据产品code 删除当天及之后的价格信息
     * @param productCode
     * @return
     */
    void deletePriceInfoByDay(String productCode,String day);

    /**
     * 根据产品code 日期 查询价格信息
     * @param productCode
     * @param day
     * @return
     */
    ProductPriceDO getProductDayDetail(String productCode, String day);

}
