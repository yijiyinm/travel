package com.example.travel.product.service;

import com.example.travel.product.dto.AddProductDTO;

import java.util.List;

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

    /**
     * 查询商品列表
     * @param productName
     * @return
     */
    List<AddProductDTO> getProductList(String productName);

    /**
     * 查询商品详情
     * @param productCode
     * @return
     */
    AddProductDTO getProductDetail(String productCode);

}
