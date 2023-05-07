package com.example.travel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.travel.dao.ProductPriceMapper;
import com.example.travel.dao.entity.ProductPriceDO;
import com.example.travel.service.ProductPriceService;
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
    public List<ProductPriceDO> getPriceInfoByDay(String productCode, String day) {
        return baseMapper.getPriceInfoByDay(productCode,day);
    }

    @Override
    public void deletePriceInfoByDay(String productCode, String day) {
         baseMapper.deletePriceInfoByDay(productCode,day);
    }
}
