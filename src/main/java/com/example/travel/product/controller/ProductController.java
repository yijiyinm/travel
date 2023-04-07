package com.example.travel.product.controller;

import com.example.travel.product.dto.AddProductDTO;
import com.example.travel.product.service.ProductService;
import com.example.travel.util.BaseRespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yijiyin
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 新增产品
     * @return
     */
    @PostMapping("addProduct")
    public BaseRespResult addProduct(@RequestBody AddProductDTO addProductDTO) {
        boolean ret = productService.addProduct(addProductDTO);
        if (ret){
            return BaseRespResult.successResult("新增成功");
        }
        return BaseRespResult.errorResult("新增失败");
    }

    /**
     * 编辑产品
     * @return
     */
    @PostMapping("updateProduct")
    public BaseRespResult updateProduct(@RequestBody AddProductDTO addProductDTO) {
        boolean ret = productService.updateProduct(addProductDTO);
        if (ret){
            return BaseRespResult.successResult("编辑成功");
        }
        return BaseRespResult.errorResult("编辑失败");
    }


    /**
     * 产品列表查询
     * @return
     */
    @PostMapping("getProductList")
    public BaseRespResult getProductList(@RequestParam(value = "productName") String productName) {
        List<AddProductDTO> dtoList = productService.getProductList(productName);
        return BaseRespResult.successResult(dtoList);
    }

    /**
     * 产品详情
     * @return
     */
    @PostMapping("getProductDetail")
    public BaseRespResult getProductDetail(@RequestParam(value = "productCode") String productCode) {
        AddProductDTO dtoList = productService.getProductDetail(productCode);
        return BaseRespResult.successResult(dtoList);
    }


}
