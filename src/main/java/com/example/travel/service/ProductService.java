package com.example.travel.service;

import com.example.travel.dto.AddProductDTO;

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

    /**
     * 小程序查询商品所有table栏
     * @return
     */
    List<String> getProductAllTableWX();

    /**
     * 小程序查询上架商品列表
     * @param label 标签
     * @return
     */
    List<AddProductDTO> getProductListWX(String label);

}
