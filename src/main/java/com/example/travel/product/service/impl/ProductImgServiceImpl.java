package com.example.travel.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.travel.product.dao.ProductImgMapper;
import com.example.travel.product.entity.ProductImgDO;
import com.example.travel.product.service.ProductImgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author yijiyin
 */
@Slf4j
@Service("productImgService")
public class ProductImgServiceImpl extends ServiceImpl<ProductImgMapper, ProductImgDO> implements ProductImgService {
    @Override
    public void deleteInfoByCode(String productCode) {
        baseMapper.deleteInfoByCode(productCode);
    }
}
