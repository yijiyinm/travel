package com.example.travel.product.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.travel.product.dao.ProductPriceMapper;
import com.example.travel.product.entity.ProductPriceDO;
import com.example.travel.product.service.ProductPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yijiyin
 */
@Slf4j
@Service("productPriceService")
public class ProductPriceServiceImpl extends ServiceImpl<ProductPriceMapper,ProductPriceDO> implements ProductPriceService {
    @Override
    public List<ProductPriceDO> getPriceInfoByProductCode(String productCode) {
        return list(Wrappers.<ProductPriceDO>lambdaQuery().eq(ProductPriceDO::getProductCode, productCode));
    }

    @Override
    public ProductPriceDO getPriceInfoByDay(String productCode, String day) {
        return baseMapper.getPriceInfoByDay(productCode,day);
    }
}
