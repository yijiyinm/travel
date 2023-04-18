package com.example.travel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.travel.dao.ProductImgMapper;
import com.example.travel.dao.entity.ProductImgDO;
import com.example.travel.service.ProductImgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<String> getUrlsByProductCode(String productCode) {
        return baseMapper.getUrlsByProductCode(productCode);
    }
}
