package com.example.travel.service;

import com.example.travel.dto.CreateOrderDTO;
import com.example.travel.dto.CreateOrderReturnDTO;
import com.example.travel.dto.SelectOrderDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author yijiyin
 */
public interface OrderService {

    /**
     * 创建支付订单
     * @param createOrderDTO
     * @return
     */
    CreateOrderReturnDTO createOrder(CreateOrderDTO createOrderDTO);

    /**
     * 支付回调
     * @param request
     * @param response
     */
    void wxPayNotify(HttpServletRequest request, HttpServletResponse response);

    /**
     * 小程序用户查询支付订单列表
     * @param openId
     * @return
     */
    List<SelectOrderDTO> getOrderListWX(String openId);

    /**
     * 小程序用户查询支付订单列表
     * @param openId
     * @return
     */
    SelectOrderDTO getOrderDetailWX(String orderCode);


}
