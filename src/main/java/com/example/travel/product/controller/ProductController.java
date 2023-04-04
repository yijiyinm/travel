package com.example.travel.product.controller;

import com.example.travel.product.dto.AddProductDTO;
import com.example.travel.product.service.ProductService;
import com.example.travel.util.BaseRespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yijiyin
 */
@RestController
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


//    /**
//     * 产品列表查询
//     * @return
//     */
//    @PostMapping("getProductList")
//    public BaseRespResult getProductList(@RequestBody AddProductDTO addProductDTO) {
//        boolean ret = productService.getProductList(addProductDTO);
//    }


}
