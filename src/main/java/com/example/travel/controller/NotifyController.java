package com.example.travel.controller;

import com.example.travel.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yijiyin
 */
@Slf4j
@RestController
@RequestMapping("notify")
public class NotifyController {
    @Resource
    private OrderService orderService;

    @PostMapping("wxPay/notify")
    public void wxPayNotify(HttpServletRequest request, HttpServletResponse response) {
        log.info("进入微信支付回调++++++++++");
        orderService.wxPayNotify(request, response);
    }

    @PostMapping("wxPay/return/notify")
    public void wxPayReturnNotify(HttpServletRequest request, HttpServletResponse response) {
        log.info("进入微信支付退款回调++++++++++");
        orderService.wxPayReturnNotify(request, response);
    }
}
