package com.example.travel.controller;

import com.example.travel.dto.SelectOrderDTO;
import com.example.travel.dto.TouristDTO;
import com.example.travel.dto.CreateOrderDTO;
import com.example.travel.dto.CreateOrderReturnDTO;
import com.example.travel.param.SelOrderListParam;
import com.example.travel.service.OrderService;
import com.example.travel.service.TouristService;
import com.example.travel.util.BaseRespResult;
import com.example.travel.util.CommenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
    public BaseRespResult createOrder(@RequestBody CreateOrderDTO createOrderDTO, HttpServletRequest request) {
        String tokeninfo = request.getHeader("tokeninfo");
//        Map<String,String> map = CommenUtils.decryptUserIdAndTokenByStr(tokeninfo);
//        String openId = map.get("openId");
        CreateOrderReturnDTO returnDTO = orderService.createOrder(createOrderDTO,"o6U8L5MZe6-rflFrybXXAkwak3D8");
        return BaseRespResult.successResult(returnDTO);
    }


    /**
     * 后台获取订单列表
     * @return
     */
    @PostMapping("getOrderList")
    public BaseRespResult getOrderList(@RequestBody SelOrderListParam param) {
        return BaseRespResult.successResult(orderService.getOrderList(param));
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
