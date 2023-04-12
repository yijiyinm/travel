package com.example.travel.order.callback;

import com.example.travel.order.service.OrderService;
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
}
