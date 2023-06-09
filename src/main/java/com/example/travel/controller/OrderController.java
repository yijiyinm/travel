package com.example.travel.controller;

import com.alibaba.fastjson.JSON;
import com.example.travel.aop.Authority;
import com.example.travel.aop.AuthorityType;
import com.example.travel.cache.CacheManager;
import com.example.travel.dto.SelectOrderDTO;
import com.example.travel.dto.TouristDTO;
import com.example.travel.dto.CreateOrderDTO;
import com.example.travel.dto.CreateOrderReturnDTO;
import com.example.travel.enums.FxsJsEnum;
import com.example.travel.param.SelOrderListParam;
import com.example.travel.service.OrderService;
import com.example.travel.service.TouristService;
import com.example.travel.util.BaseRespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yijiyin
 */
@RestController
@Authority(authoritytype = AuthorityType.CHECK_LOGIN)
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

        String openId = CacheManager.get(tokeninfo);
        CreateOrderReturnDTO returnDTO = orderService.createOrder(createOrderDTO,openId);

        //CreateOrderReturnDTO returnDTO = orderService.createOrder(createOrderDTO,"o6U8L5MZe6-rflFrybXXAkwak3D8");
        return BaseRespResult.successResult(returnDTO);
    }

    /**
     * 待支付订单支付
     * @return
     */
    @PostMapping("payOrder")
    public BaseRespResult payOrder(@RequestBody CreateOrderReturnDTO createOrderReturnDTO) {


        CreateOrderReturnDTO returnDTO = orderService.payOrder(createOrderReturnDTO.getOrderCode());
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
     * 手动操作退款
     * @return
     */
    @PostMapping("orderRefund")
    public BaseRespResult orderRefund(@RequestBody SelOrderListParam param) {
        Boolean ret = orderService.orderRefund(param.getOrderCode(),param.getRefundAmount());
        if (ret){
            return BaseRespResult.successResult("申请退款成功");
        }
        return BaseRespResult.errorResult("申请退款失败");
    }

    /**
     * 结算订单
     * @return
     */
    @PostMapping("fsxJsByOrderCode")
    public BaseRespResult fsxJsByOrderCode(@RequestBody SelOrderListParam param) {
        Boolean ret = orderService.fsxJsByOrderCode(param.getOrderCodes());
        if (ret){
            return BaseRespResult.successResult("结算成功");
        }
        return BaseRespResult.errorResult("结算失败");
    }

    /**
     * 获取结算枚举
     * @return
     */
    @Authority(authoritytype = AuthorityType.NOCHECK)
    @GetMapping("getFsxJsEnum")
    public BaseRespResult getFsxJsEnum() {
        HashMap<Integer, String> objectObjectHashMap = new HashMap<>();
        for (FxsJsEnum fxsJsEnum: FxsJsEnum.values()){
            objectObjectHashMap.put(fxsJsEnum.getDex(), fxsJsEnum.getName());
        }
        return BaseRespResult.successResult(objectObjectHashMap);
    }

    /**
     * 小程序获取订单列表
     * @return
     */
    @PostMapping("getOrderListWX")
    public BaseRespResult getOrderListWX(HttpServletRequest request,@RequestBody SelectOrderDTO selectOrderDTO) {

        // 验证登录
        String tokeninfo = request.getHeader("tokeninfo");

        String openId = CacheManager.get(tokeninfo);
        //通过token 得到openId并传入
        List<SelectOrderDTO> selectOrderDTOS = orderService.getOrderListWX(openId,selectOrderDTO);
        return BaseRespResult.successResult(selectOrderDTOS);
    }

    /**
     * 小程序获取订单列表-分销商订单列表
     * @return
     */
    @PostMapping("getFxsOrderListWX")
    public BaseRespResult getFxsOrderListWX(@RequestBody SelOrderListParam param) {
        List<SelectOrderDTO> selectOrderDTOS = orderService.getFxsOrderListWX(param.getFxsCode());
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
    public BaseRespResult addContactPerson(@RequestBody TouristDTO touristDTO,HttpServletRequest request) {
        // 通过token 得到openId并传入
        String tokeninfo = request.getHeader("tokeninfo");

        String openId = CacheManager.get(tokeninfo);
        Boolean ret = touristService.addTourist(touristDTO,openId);
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
    public BaseRespResult getContactPersonList(HttpServletRequest request) {
        // 通过token 得到openId并传入
        String tokeninfo = request.getHeader("tokeninfo");

        String openId = CacheManager.get(tokeninfo);
        List<TouristDTO> touristDTOS = touristService.getTouristList(openId);
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
