package com.example.travel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.travel.dao.entity.OrderDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * @author yijiyin
 */
public interface OrderMapper extends BaseMapper<OrderDO> {
    /**
     * 根据商户订单号查询订单
     * @param outTradeNo
     * @return
     */
    @Select("select * from user_order where out_trade_no=#{outTradeNo}")
    OrderDO getOrderByOutTradeNo(@Param("outTradeNo") String outTradeNo);

    /**
     * 根据商品对应天数购买库存
     * @param productCode
     * @param dayDate
     * @return
     */
    @Select("select sum(num) from user_order where product_code=#{productCode} and chu_xing_date=#{dayDate} and status = 2")
    Integer getDaySumByProductCode(@Param("productCode") String productCode,@Param("dayDate") String dayDate);
}
