package com.example.travel.order.controller;

import com.example.travel.order.dto.SelectOrderDTO;
import com.example.travel.order.dto.TouristDTO;
import com.example.travel.order.dto.CreateOrderDTO;
import com.example.travel.order.dto.CreateOrderReturnDTO;
import com.example.travel.order.service.OrderService;
import com.example.travel.order.service.TouristService;
import com.example.travel.util.BaseRespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yijiyin
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private TouristService touristService;

    /**
     * 订单创建
     * @return
     */
    @PostMapping("createOrder")
    public BaseRespResult createOrder(@RequestBody CreateOrderDTO createOrderDTO) {
        CreateOrderReturnDTO returnDTO = orderService.createOrder(createOrderDTO);
        return BaseRespResult.successResult(returnDTO);
    }


    /**
     * 小程序获取订单列表
     * @return
     */
    @PostMapping("getOrderListWX")
    public BaseRespResult getOrderListWX() {
        // todo 通过token 得到openId并传入
        List<SelectOrderDTO> selectOrderDTOS = orderService.getOrderListWX("");
        return BaseRespResult.successResult(selectOrderDTOS);
    }

    /**
     * 小程序获取订单详情
     * @return
     */
    @PostMapping("getOrderDetailWX")
    public BaseRespResult getOrderDetailWX(@RequestBody CreateOrderReturnDTO createOrderReturnDTO) {
        System.out.println(createOrderReturnDTO);
        SelectOrderDTO selectOrderDTO = orderService.getOrderDetailWX(createOrderReturnDTO.getOrderCode());
        return BaseRespResult.successResult(selectOrderDTO);
    }




    /**
     * 新增游客信息
     * @return
     */
    @PostMapping("addContactPerson")
    public BaseRespResult addContactPerson(@RequestBody TouristDTO touristDTO) {
        // todo 通过token 得到openId并传入
        Boolean ret = touristService.addTourist(touristDTO,"");
        if (ret){
            return BaseRespResult.successResult("新增成功");
        }
        return BaseRespResult.errorResult("新增失败");
    }

    /**
     * 查询用户所有游客信息
     * @return
     */
    @PostMapping("getContactPersonList")
    public BaseRespResult getContactPersonList() {
        // todo 通过token 得到openId并传入
        List<TouristDTO> touristDTOS = touristService.getTouristList("");
        return BaseRespResult.successResult(touristDTOS);
    }

    /**
     * 修改游客信息
     * @return
     */
    @PostMapping("updateContactPerson")
    public BaseRespResult updateContactPerson(@RequestBody TouristDTO touristDTO) {
        Boolean ret = touristService.updateTourist(touristDTO);
        if (ret){
            return BaseRespResult.successResult("修改成功");
        }
        return BaseRespResult.errorResult("修改失败");
    }

    /**
     * 删除游客信息
     * @return
     */
    @PostMapping("deleteContactPerson")
    public BaseRespResult deleteContactPerson(@RequestBody TouristDTO touristDTO) {
        Boolean ret = touristService.deleteTourist(touristDTO.getId());
        if (ret){
            return BaseRespResult.successResult("删除成功");
        }
        return BaseRespResult.errorResult("删除失败");
    }


}
