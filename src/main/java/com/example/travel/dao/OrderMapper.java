package com.example.travel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.travel.dao.entity.OrderDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
}
