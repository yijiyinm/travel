package com.example.travel.controller;

import com.example.travel.aop.Authority;
import com.example.travel.aop.AuthorityType;
import com.example.travel.dto.AddProductDTO;
import com.example.travel.service.ProductService;
import com.example.travel.util.BaseRespResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
     * 上传产品图片
     * @return
     */
    @PostMapping("uploadImg")
    @ResponseBody
    public BaseRespResult uploadImg(MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response) {
        String imgUrl = productService.uploadImg(multipartFile,request,response);
        return BaseRespResult.successResult(imgUrl);
    }

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
     * 上架/下架商品
     * @return
     */
    @PostMapping("updateProductStatus")
    public BaseRespResult updateProductStatus(@RequestBody AddProductDTO addProductDTO) {
        boolean ret = productService.updateProductStatus(addProductDTO.getProductCode(),addProductDTO.getStatus());
        if (ret){
            return BaseRespResult.successResult("操作成功");
        }
        return BaseRespResult.errorResult("操作失败");
    }

    /**
     * 删除下架商品
     * @return
     */
    @PostMapping("deleteProduct")
    public BaseRespResult deleteProduct(@RequestBody AddProductDTO addProductDTO) {
        boolean ret = productService.deleteProduct(addProductDTO.getProductCode());
        if (ret){
            return BaseRespResult.successResult("操作成功");
        }
        return BaseRespResult.errorResult("操作失败");
    }


    /**
     * 产品列表查询
     * @return
     */
    @PostMapping("getProductList")
    public BaseRespResult getProductList(@RequestBody AddProductDTO param) {
        List<AddProductDTO> dtoList = productService.getProductList(param.getProductName());
        return BaseRespResult.successResult(dtoList);
    }

    /**
     * 产品详情
     * @return
     */
    @GetMapping("getProductDetail")
    public BaseRespResult getProductDetail(@RequestParam(value = "productCode") String productCode) {
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
    public BaseRespResult getProductListWX(HttpServletRequest httpServletRequest) {
        String label = httpServletRequest.getParameter("label");
        List<AddProductDTO> addProductDTOS = productService.getProductListWX(label);
        return BaseRespResult.successResult(addProductDTOS);
    }




}
