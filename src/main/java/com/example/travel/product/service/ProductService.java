package com.example.travel.product.service;

import com.example.travel.product.dto.AddProductDTO;

/**
 * @author yijiyin
 */
public interface ProductService {

    /**
     * 添加商品
     * @param addProductDTO
     * @return
     */
    boolean addProduct(AddProductDTO addProductDTO);

    /**
     * 编辑商品
     * @param addProductDTO
     * @return
     */
    boolean updateProduct(AddProductDTO addProductDTO);

}
