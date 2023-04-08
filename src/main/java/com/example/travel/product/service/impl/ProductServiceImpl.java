package com.example.travel.product.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.travel.product.dto.AddProductDTO;
import com.example.travel.product.dto.ProductPriceDTO;
import com.example.travel.product.dao.ProductMapper;
import com.example.travel.product.entity.ProductDO;
import com.example.travel.product.entity.ProductImgDO;
import com.example.travel.product.entity.ProductPriceDO;
import com.example.travel.product.service.ProductImgService;
import com.example.travel.product.service.ProductPriceService;
import com.example.travel.product.service.ProductService;
import com.example.travel.util.GenerateCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author yijiyin
 */
@Slf4j
@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductDO> implements ProductService {

    @Autowired
    private ProductImgService productImgService;
    @Autowired
    private ProductPriceService productPriceService;

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
            productDO.setLabel(addProductDTO.getLabel());
            productDO.setSequence(addProductDTO.getSequence());
            productDO.setMainUrl(addProductDTO.getMainUrl());
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
            productDO.setLabel(addProductDTO.getLabel());
            productDO.setSequence(addProductDTO.getSequence());
            productDO.setMainUrl(addProductDTO.getMainUrl());
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

    @Override
    public List<AddProductDTO> getProductList(String productName) {
        List<ProductDO> productDOS = list(Wrappers.<ProductDO>lambdaQuery().like(StringUtils.isNotBlank(productName),ProductDO::getProductName, productName).orderByAsc(ProductDO::getSequence));
        List<AddProductDTO> addProductDTOS = new ArrayList<>();
        for (ProductDO productDO : productDOS) {
            AddProductDTO addProductDTO = new AddProductDTO();
            addProductDTO.setDescription(productDO.getDescription());
            addProductDTO.setProductName(productDO.getProductName());
            addProductDTO.setProductCode(productDO.getProductCode());
            addProductDTO.setStatus(productDO.getStatus());
            addProductDTO.setLabel(productDO.getLabel());
            addProductDTO.setSequence(productDO.getSequence());
            addProductDTOS.add(addProductDTO);
        }
        return addProductDTOS;
    }

    @Override
    public AddProductDTO getProductDetail(String productCode) {
        ProductDO productDO = getOne(Wrappers.<ProductDO>lambdaQuery().eq(ProductDO::getProductCode, productCode));
        AddProductDTO addProductDTO = new AddProductDTO();
        addProductDTO.setDescription(productDO.getDescription());
        addProductDTO.setProductName(productDO.getProductName());
        addProductDTO.setProductCode(productDO.getProductCode());
        addProductDTO.setLabel(productDO.getLabel());
        addProductDTO.setSequence(productDO.getSequence());
        addProductDTO.setMainUrl(productDO.getMainUrl());
        addProductDTO.setStatus(productDO.getStatus());

        // 价格信息
        List<ProductPriceDTO> productPriceDTOS = new ArrayList<>();
        List<ProductPriceDO> productPriceDOS = productPriceService.getPriceInfoByProductCode(productCode);
        for (ProductPriceDO priceDO : productPriceDOS){
            ProductPriceDTO productPriceDTO = new ProductPriceDTO();
            productPriceDTO.setDayDate(priceDO.getDayDate());
            productPriceDTO.setPrice(priceDO.getPrice());
            productPriceDTO.setPriceCr(priceDO.getPriceCr());
            productPriceDTO.setPriceEt(priceDO.getPriceEt());
            productPriceDTOS.add(productPriceDTO);
        }
        addProductDTO.setPriceDTOS(productPriceDTOS);

        // 图片信息
        List<String> urls = productImgService.getUrlsByProductCode(productCode);
        addProductDTO.setUrls(urls);
        return addProductDTO;
    }

    @Override
    public List<String> getProductAllTableWX() {
        return baseMapper.getProductAllTableWX();
    }

    @Override
    public List<AddProductDTO> getProductListWX(String label) {
        List<ProductDO> productDOS = list(Wrappers.<ProductDO>lambdaQuery().eq(ProductDO::getLabel, label).eq(ProductDO::getStatus,2).orderByAsc(ProductDO::getSequence));
        List<AddProductDTO> addProductDTOS = new ArrayList<>();
        for (ProductDO productDO : productDOS) {
            AddProductDTO addProductDTO = new AddProductDTO();
            addProductDTO.setDescription(productDO.getDescription());
            addProductDTO.setProductName(productDO.getProductName());
            addProductDTO.setProductCode(productDO.getProductCode());
            addProductDTO.setLabel(productDO.getLabel());
            addProductDTO.setMainUrl(productDO.getMainUrl());
            addProductDTO.setSequence(productDO.getSequence());
            // 价格信息

            // 今天的日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            date = calendar.getTime();
            String day = sdf.format(date);

            ProductPriceDO priceDO = productPriceService.getPriceInfoByDay(productDO.getProductCode(),day);
            if (priceDO!=null) {
                addProductDTO.setPrice(priceDO.getPrice());
                addProductDTO.setPriceCr(priceDO.getPriceCr());
            }
            addProductDTOS.add(addProductDTO);
        }
        return addProductDTOS;
    }
}
