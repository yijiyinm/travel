package com.example.travel.order.controller;

import com.example.travel.order.service.OrderService;
import com.example.travel.product.dto.AddProductDTO;
import com.example.travel.util.BaseRespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yijiyin
 */
@RestController
@RequestMapping("/order")
public class OrderController {

//    @Autowired
//    private OrderService orderService;
//
//    /**
//     * 订单创建
//     * @return
//     */
//    @PostMapping("createOrder")
//    public BaseRespResult createOrder(@RequestBody AddProductDTO addProductDTO) {
//        boolean ret = orderService.createOrder(addProductDTO);
//        if (ret){
//            return BaseRespResult.successResult("新增成功");
//        }
//        return BaseRespResult.errorResult("新增失败");
//    }
}
