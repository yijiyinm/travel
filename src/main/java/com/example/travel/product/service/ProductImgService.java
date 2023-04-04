package com.example.travel.product.service;

/**
 * @author yijiyin
 */
public interface ProductImgService {
    /**
     * 编辑产品时删除其所有的图片
     * @param productCode
     */
    void deleteInfoByCode(String productCode);
}
