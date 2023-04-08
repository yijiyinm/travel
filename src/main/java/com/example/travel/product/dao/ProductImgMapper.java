package com.example.travel.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.travel.product.entity.ProductImgDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author yijiyin
 */
public interface ProductImgMapper extends BaseMapper<ProductImgDO> {

    /**
     * 清空
     * @param productCode
     */
    @Delete("delete from product_img where product_code = #{productCode}")
    void deleteInfoByCode(@Param("productCode") String productCode);

    /**
     * 获取产品所有url
     * @param productCode
     * @return
     */
    @Select("select img_url from product_img where product_code=#{productCode}")
    List<String> getUrlsByProductCode(@Param("productCode") String productCode);
}
