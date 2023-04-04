package com.example.travel.product.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.travel.product.dto.AddProductDTO;
import com.example.travel.product.dto.ProductPriceDTO;
import com.example.travel.product.dao.ProductMapper;
import com.example.travel.product.entity.ProductDO;
import com.example.travel.product.entity.ProductImgDO;
import com.example.travel.product.entity.ProductPriceDO;
import com.example.travel.product.service.ProductImgService;
import com.example.travel.product.service.ProductService;
import com.example.travel.util.GenerateCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yijiyin
 */
@Slf4j
@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductDO> implements ProductService {

    @Autowired
    private ProductImgService productImgService;

    @Override
    public boolean addProduct(AddProductDTO addProductDTO) {
        try {
            String productCode = GenerateCodeUtil.createCode(12);
            // 产品主表信息入库
            ProductDO productDO = new ProductDO();
            productDO.setProductCode(productCode);
            productDO.setProductName(addProductDTO.getProductName());
            productDO.setDescription(addProductDTO.getDescription());
            productDO.setStatus(addProductDTO.getStatus());
            productDO.insert();

            // 图片信息存储
            for(String url :addProductDTO.getUrls()){
                ProductImgDO productImgDO = new ProductImgDO();
                productImgDO.setProductCode(productCode);
                productImgDO.setImgUrl(url);
                productImgDO.insert();
            }
            // 每日/对应价格存储
            for (ProductPriceDTO priceDTO : addProductDTO.getPriceDTOS()){
                ProductPriceDO priceDO = new ProductPriceDO();
                priceDO.setProductCode(productCode);
                priceDO.setDayDate(priceDTO.getDayDate());
                priceDO.setPrice(priceDTO.getPrice());
                priceDO.setPriceCr(priceDTO.getPriceCr());
                priceDO.setPriceEt(priceDTO.getPriceEt());
                priceDO.insert();
            }
        } catch (Exception e) {
            log.error("产品新增异常：{}",e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateProduct(AddProductDTO addProductDTO) {
        try {


            // 产品主表信息入库
            ProductDO productDO = getOne(Wrappers.<ProductDO>lambdaQuery().eq(ProductDO::getProductCode, addProductDTO.getProductCode()));
            if (productDO == null) {
                log.error("数据异常，商品不存在");
                return false;
            }
            productDO.setProductName(addProductDTO.getProductName());
            productDO.setDescription(addProductDTO.getDescription());
            productDO.setStatus(addProductDTO.getStatus());
            productDO.updateById();

            // 删除之前的照片 重新保存
            productImgService.deleteInfoByCode(addProductDTO.getProductCode());
            // 图片信息存储
            for(String url :addProductDTO.getUrls()){
                ProductImgDO productImgDO = new ProductImgDO();
                productImgDO.setProductCode(productDO.getProductCode());
                productImgDO.setImgUrl(url);
                productImgDO.insert();
            }

            // 删除当前时间及之后的日期数据 再保存

            // 每日/对应价格存储
            for (ProductPriceDTO priceDTO : addProductDTO.getPriceDTOS()){
                ProductPriceDO priceDO = new ProductPriceDO();
                priceDO.setProductCode(productDO.getProductCode());
                priceDO.setDayDate(priceDTO.getDayDate());
                priceDO.setPrice(priceDTO.getPrice());
                priceDO.setPriceCr(priceDTO.getPriceCr());
                priceDO.setPriceEt(priceDTO.getPriceEt());
                priceDO.insert();
            }
        } catch (Exception e) {
            log.error("产品新增异常：{}",e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
