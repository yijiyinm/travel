package com.example.travel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.travel.dao.entity.OrderDO;
import com.example.travel.dto.CreateOrderDTO;
import com.example.travel.dto.CreateOrderReturnDTO;
import com.example.travel.dto.SelectOrderDTO;
import com.example.travel.param.SelOrderListParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
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
     * 待支付订单支付
     * @param orderCode
     * @return
     */
    CreateOrderReturnDTO payOrder(String orderCode);

    /**
     * 支付回调
     * @param request
     * @param response
     */
    void wxPayNotify(HttpServletRequest request, HttpServletResponse response);

    /**
     * 退款回调
     * @param request
     * @param response
     */
    void wxPayReturnNotify(HttpServletRequest request, HttpServletResponse response);

    /**
     * 小程序用户查询支付订单列表
     * @param openId
     * @return
     */
    List<SelectOrderDTO> getOrderListWX(String openId,SelectOrderDTO selectOrderDTO);

    /**
     * 小程序用户查询分销商订单列表
     * @param fxsCode
     * @return
     */
    List<SelectOrderDTO> getFxsOrderListWX(String fxsCode);

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
    Boolean orderRefund(String orderCode, BigDecimal refundAmount);

    /**
     * 查询对应商品当天卖出多少库存
     * @param productCode 商品编码
     * @param dayDate 价格日期
     * @return
     */
    Integer getDaySumByProductCode(String productCode, Date dayDate);



    /**
     * 小程序用户查询支付订单列表
     * @param selectOrderDTO
     * @return
     */
    List<OrderDO> getAllOrder(SelectOrderDTO selectOrderDTO);


    Boolean fsxJsByOrderCode(String orderCode);
}
