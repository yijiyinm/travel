package com.example.travel.order.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.travel.order.dao.OrderMapper;
import com.example.travel.order.dto.CreateOrderDTO;
import com.example.travel.order.dto.CreateOrderReturnDTO;
import com.example.travel.order.dto.SelectOrderDTO;
import com.example.travel.order.dto.TouristDTO;
import com.example.travel.order.entity.OrderDO;
import com.example.travel.order.enums.OrderStatusEnum;
import com.example.travel.order.service.OrderService;
import com.example.travel.order.service.TouristService;
import com.example.travel.product.dto.AddProductDTO;
import com.example.travel.product.service.ProductService;
import com.example.travel.util.AppInfoEnum;
import com.example.travel.util.GenerateCodeUtil;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.RSAConfig;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yijiyin
 */
@Slf4j
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderMapper,OrderDO> implements OrderService {
    @Resource
    private ProductService productService;
    @Resource
    private TouristService touristService;

    public static String merchantId = "";
    public static String privateKeyPath = "";
    public static String merchantSerialNumber = "";
    public static String wechatPayCertificatePath = "";
    public static JsapiServiceExtension jsapiServiceExtension;

    @Override
    public CreateOrderReturnDTO createOrder(CreateOrderDTO param) {

        OrderDO orderDO = new OrderDO();
        String orderCode = "DD"+GenerateCodeUtil.createCode(18);
        PrepayWithRequestPaymentResponse paymentResponse=null;
        try {
            // 根据token 查询对应用户openId
            String openId = "";

            // 初始化商户配置
            RSAConfig config =
                    new RSAConfig.Builder()
                            .merchantId(merchantId)
                            // 使用 com.wechat.pay.java.core.util 中的函数从本地文件中加载商户私钥，商户私钥会用来生成请求的签名
                            .privateKeyFromPath(privateKeyPath)
                            .merchantSerialNumber(merchantSerialNumber)
                            .wechatPayCertificatesFromPath(wechatPayCertificatePath)
                            .build();
            // 初始化服务
            jsapiServiceExtension =
                    new JsapiServiceExtension.Builder()
                            .config(config)
                            .signType("RSA")
                            .build();

            String outTradeNo = "OTN"+GenerateCodeUtil.createCode(26);
            PrepayRequest prepayRequest = new PrepayRequest();
            prepayRequest.setAppid(AppInfoEnum.APP_ID.getValue());
            prepayRequest.setMchid(AppInfoEnum.MCH_ID.getValue());
            prepayRequest.setDescription("商品描述");
            prepayRequest.setOutTradeNo(outTradeNo);
            // todo 回调地址
            prepayRequest.setNotifyUrl("");
            Amount amount = new Amount();
            amount.setTotal(param.getPayPrice().multiply(BigDecimal.valueOf(100)).intValue());
            amount.setCurrency("CNY");
            prepayRequest.setAmount(amount);
            Payer payer = new Payer();
            payer.setOpenid(openId);
            prepayRequest.setPayer(payer);
            // todo 订单有效时间或许是否需要待确认
            // prepayRequest.setTimeExpire("");
            paymentResponse = jsapiServiceExtension.prepayWithRequestPayment(prepayRequest);
            log.info("预支付下单结果：{}",paymentResponse);
            orderDO.setOrderCode(orderCode);
            orderDO.setNum(param.getCrNum()+param.getEtNum());
            orderDO.setPrice(param.getPayPrice());
            orderDO.setProductCode(param.getProductCode());
            orderDO.setProductName(param.getProductName());
            orderDO.setStatus(OrderStatusEnum.WAIT_PAY.getStatus());
            orderDO.setOutTradeNo(outTradeNo);
            orderDO.setPrePayId(paymentResponse.getPackageVal());
            String touristIds = "";
            for (int i=0;i<param.getTouristIds().size(); i++) {
                if (i==0) {
                    touristIds+=param.getTouristIds().get(i);
                } else {
                    touristIds+=","+param.getTouristIds().get(i);
                }
            }
            orderDO.setTouristIds(touristIds);
            orderDO.setOpenId(openId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        orderDO.insert();
        CreateOrderReturnDTO returnDTO = new CreateOrderReturnDTO();
        returnDTO.setOrderCode(orderCode);
        returnDTO.setPackageVal(paymentResponse.getPackageVal());
        returnDTO.setPaySign(paymentResponse.getPaySign());
        returnDTO.setTimeStamp(paymentResponse.getTimeStamp());
        returnDTO.setNonceStr(paymentResponse.getNonceStr());
        returnDTO.setAppId(paymentResponse.getAppId());
        returnDTO.setSignType(paymentResponse.getSignType());
        return returnDTO;
    }

    @Override
    public void wxPayNotify(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public List<SelectOrderDTO> getOrderListWX(String openId) {
        try {
            List<SelectOrderDTO> selectOrderDTOS = new ArrayList<>();
            List<OrderDO> orderDOS = list(Wrappers.<OrderDO>lambdaQuery().eq(OrderDO::getOpenId, openId));
            for (OrderDO orderDO : orderDOS){
                SelectOrderDTO selectOrderDTO = new SelectOrderDTO();
                selectOrderDTO.setProductName(orderDO.getProductName());
                selectOrderDTO.setPayPrice(orderDO.getPrice());
                selectOrderDTO.setOrderStatus(orderDO.getStatus());
                selectOrderDTO.setOrderCode(orderDO.getOrderCode());
                selectOrderDTO.setNum(orderDO.getNum());
                // 产品信息
                AddProductDTO addProductDTO = productService.getProductDetail(orderDO.getProductCode());
                selectOrderDTO.setMainUrl(addProductDTO.getMainUrl());
                selectOrderDTO.setDescription(addProductDTO.getDescription());
                selectOrderDTOS.add(selectOrderDTO);
            }
            return selectOrderDTOS;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("小程序查询订单列表失败");
        }
        return null;
    }

    @Override
    public SelectOrderDTO getOrderDetailWX(String orderCode) {
        try {
            OrderDO orderDO = getOne(Wrappers.<OrderDO>lambdaQuery().eq(OrderDO::getOrderCode, orderCode));
            SelectOrderDTO selectOrderDTO = new SelectOrderDTO();
            selectOrderDTO.setPayPrice(orderDO.getPrice());
            selectOrderDTO.setOrderStatus(orderDO.getStatus());
            selectOrderDTO.setNum(orderDO.getNum());
            // 产品信息
            AddProductDTO addProductDTO = productService.getProductDetail(orderDO.getProductCode());
            selectOrderDTO.setProductInfo(addProductDTO);
            // 游客信息
            String[] touristList = orderDO.getTouristIds().split(",");
            List<TouristDTO> touristDTOS = new ArrayList<>();
            for (String touristId : touristList) {
                TouristDTO touristDTO = touristService.getTouristById(Long.valueOf(touristId));
                touristDTOS.add(touristDTO);
            }
            selectOrderDTO.setTouristInfo(touristDTOS);
            return selectOrderDTO;
        } catch (NumberFormatException e) {
            log.error("查询订单详情错误");
            e.printStackTrace();

        }
        return null;
    }

    /**
     * desc 获取微信支付回调请求参数
     *
     */
//    private Map<String, String> getWxNotifyParamMap(HttpServletRequest request) {
//// 构造 RequestParam
//        RequestParam requestParam = new RequestParam.Builder()
//                .serialNumber(wechatPayCertificateSerialNumber)
//                .nonce(nonce)
//                .signature(signature)
//                .timestamp(timestamp)
//                .body(requestBody)
//                .build();
//
//// 如果已经初始化了 RSAAutoCertificateConfig，可直接使用
//// 没有的话，则构造一个
//        NotificationConfig config = new RSAAutoCertificateConfig.Builder()
//                .merchantId(merchantId)
//                .privateKeyFromPath(privateKeyPath)
//                .merchantSerialNumber(merchantSerialNumber)
//                .apiV3Key("apiV3key")
//                .build();
//
//// 初始化 NotificationParser
//        NotificationParser parser = new NotificationParser(config);
//
//// 以支付通知回调为例，验签、解密并转换成 Transaction
//        Transaction transaction = parser.parse(requestParam, Transaction.class);
//    }
}
