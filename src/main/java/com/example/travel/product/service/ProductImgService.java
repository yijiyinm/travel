package com.example.travel.product.service;

import java.util.List;

/**
 * @author yijiyin
 */
public interface ProductImgService {
    /**
     * 编辑产品时删除其所有的图片
     * @param productCode
     */
    void deleteInfoByCode(String productCode);

    /**
     * 查询产品所有照片
     * @param productCode
     * @return
     */
    List<String> getUrlsByProductCode(String productCode);
}
