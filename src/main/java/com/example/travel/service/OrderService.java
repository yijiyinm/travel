package com.example.travel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.travel.dto.CreateOrderDTO;
import com.example.travel.dto.CreateOrderReturnDTO;
import com.example.travel.dto.SelectOrderDTO;
import com.example.travel.param.SelOrderListParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
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
    CreateOrderReturnDTO createOrder(CreateOrderDTO createOrderDTO,String openId);

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
     * 后台查询支付订单列表
     * @param param
     * @return
     */
    Page<SelectOrderDTO> getOrderList(SelOrderListParam param);



    /**
     * 小程序用户查询支付订单详情
     * @param orderCode
     * @return
     */
    SelectOrderDTO getOrderDetailWX(String orderCode);


    /**
     * 退款
     * @param orderCode
     * @return
     */
    Boolean orderRefund(String orderCode);


}
