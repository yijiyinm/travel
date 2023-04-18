package com.example.travel.controller;

import com.example.travel.aop.Authority;
import com.example.travel.aop.AuthorityType;
import com.example.travel.dto.AddProductDTO;
import com.example.travel.service.ProductService;
import com.example.travel.util.BaseRespResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yijiyin
 */
@Slf4j
@RestController
@RequestMapping("/product")
@Authority(authoritytype = AuthorityType.CHECK_LOGIN)
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
    @GetMapping("getProductDetail")
    public BaseRespResult getProductDetail(@RequestParam(value = "productCode") String productCode) {
        log.error("asd");
        AddProductDTO dtoList = productService.getProductDetail(productCode);
        BaseRespResult baseRespResult = BaseRespResult.successResult(dtoList);
        return baseRespResult;
    }







    /**
     * 小程序查询商品所有table栏
     */
    @GetMapping("getProductAllTableWX")
    public BaseRespResult getProductAllTableWX() {
        List<String> strings = productService.getProductAllTableWX();
        return BaseRespResult.successResult(strings);
    }

    /**
     * 小程序查询上架商品列表
     */
    @GetMapping("getProductListWX")
    public BaseRespResult getProductListWX(@RequestParam(value = "label") String label) {
        List<AddProductDTO> addProductDTOS = productService.getProductListWX(label);
        return BaseRespResult.successResult(addProductDTOS);
    }




}
