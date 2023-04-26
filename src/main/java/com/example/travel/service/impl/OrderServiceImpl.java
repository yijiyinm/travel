package com.example.travel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.travel.dao.OrderMapper;
import com.example.travel.dto.CreateOrderDTO;
import com.example.travel.dto.CreateOrderReturnDTO;
import com.example.travel.dto.SelectOrderDTO;
import com.example.travel.dto.TouristDTO;
import com.example.travel.dao.entity.OrderDO;
import com.example.travel.enums.OrderStatusEnum;
import com.example.travel.param.SelOrderListParam;
import com.example.travel.service.OrderService;
import com.example.travel.service.TouristService;
import com.example.travel.dto.AddProductDTO;
import com.example.travel.service.ProductService;
import com.example.travel.util.AppInfoEnum;
import com.example.travel.util.GenerateCodeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import com.wechat.pay.java.core.RSAConfig;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public static String merchantId = AppInfoEnum.MCH_ID.getValue();
    public static String privateKeyPath = "/Users/yijiyin/Documents/IDEAAddress/TRAVEL/travel-service/src/main/resources/apiclient_key.pem";
    public static String merchantSerialNumber = "5B19E1719D15E8F1C7F98CA840E5EDC30DF1F5F1";
    public static String wechatPayCertificatePath = "/Users/yijiyin/Documents/IDEAAddress/TRAVEL/travel-service/src/main/resources/apiclient_cert.pem";
    public static JsapiServiceExtension jsapiServiceExtension;

    @Override
    public CreateOrderReturnDTO createOrder(CreateOrderDTO param,String openId) {

        OrderDO orderDO = new OrderDO();
        String orderCode = "DD"+GenerateCodeUtil.createCode(18);
        PrepayWithRequestPaymentResponse paymentResponse=null;
        try {
              String outTradeNo = "OTN"+GenerateCodeUtil.createCode(20);
            // 根据token 查询对应用户openId
            //String openId = "oV5OM5SM-_UH1bXnZqqHWWCf4tGA";

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

            PrepayRequest prepayRequest = new PrepayRequest();
            prepayRequest.setAppid(AppInfoEnum.APP_ID.getValue());
            prepayRequest.setMchid(merchantId);
            prepayRequest.setDescription("商品描述");
            prepayRequest.setOutTradeNo(outTradeNo);
            // todo 回调地址
            prepayRequest.setNotifyUrl("https://www.baidu.com");
            Amount amount = new Amount();
            amount.setTotal(param.getPayPrice().multiply(BigDecimal.valueOf(100)).intValue());
            amount.setCurrency("CNY");
            prepayRequest.setAmount(amount);
            Payer payer = new Payer();
            payer.setOpenid(openId);
            prepayRequest.setPayer(payer);
            // todo 订单有效时间或许是否需要待确认
            // prepayRequest.setTimeExpire("");
            log.info("预支付入参：{}",prepayRequest);
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
            //orderDO.setPrePayId("cspayId");
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
            log.error("订单创建错误");
            e.printStackTrace();
            return null;
        }
        orderDO.setCreateDate(new Date());
        orderDO.setUpdateDate(orderDO.getCreateDate());
        orderDO.insert();
        CreateOrderReturnDTO returnDTO = new CreateOrderReturnDTO();
        returnDTO.setOrderCode(orderCode);
        returnDTO.setPackageVal(paymentResponse.getPackageVal());
        returnDTO.setPaySign(paymentResponse.getPaySign());
        returnDTO.setTimeStamp(paymentResponse.getTimeStamp());
        returnDTO.setNonceStr(paymentResponse.getNonceStr());
        returnDTO.setAppId(paymentResponse.getAppId());
        returnDTO.setSignType(paymentResponse.getSignType());
//        returnDTO.setPackageVal("prepay='cs'");
//        returnDTO.setPaySign("sign");
//        returnDTO.setTimeStamp("2423231423");
//        returnDTO.setNonceStr("345fgser32rf3");
//        returnDTO.setAppId("appid231");
//        returnDTO.setSignType("SNY");
        return returnDTO;
    }

//    @Override
//    public CreateOrderReturnDTO createOrder(CreateOrderDTO param,String openId) throws FileNotFoundException {
//
//
//        // 示例：私钥存储在文件
//        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(
//                new FileInputStream("/path/to/apiclient_key.pem"));
//
//        //示例：私钥为String字符串
////        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(
////                new ByteArrayInputStream(privateKey.getBytes("utf-8")));
//
//        //...
//        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
//                .withMerchant(merchantId, merchantSerialNumber, merchantPrivateKey)
//                .withWechatPay(wechatPayCertificates);
//// ... 接下来，你仍然可以通过builder设置各种参数，来配置你的HttpClient
//
//// 通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签
//        CloseableHttpClient httpClient = builder.build();
//
//
//        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi");
//        httpPost.addHeader("Accept", "application/json");
//        httpPost.addHeader("Content-type","application/json; charset=utf-8");
//
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        ObjectNode rootNode = objectMapper.createObjectNode();
//        rootNode.put("mchid","1900009191")
//                .put("appid", "wxd678efh567hg6787")
//                .put("description", "Image形象店-深圳腾大-QQ公仔")
//                .put("notify_url", "https://www.weixin.qq.com/wxpay/pay.php")
//                .put("out_trade_no", "1217752501201407033233368018");
//        rootNode.putObject("amount")
//                .put("total", 1);
//        rootNode.putObject("payer")
//                .put("openid", "oUpF8uMuAJO_M2pxb1Q9zNjWeS6o");
//
//        objectMapper.writeValue(bos, rootNode);
//
//        httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
//        CloseableHttpResponse response = httpClient.execute(httpPost);
//
//        String bodyAsString = EntityUtils.toString(response.getEntity());
//        System.out.println(bodyAsString);
//
//
//
//
//        OrderDO orderDO = new OrderDO();
//        String orderCode = "DD"+GenerateCodeUtil.createCode(18);
//        PrepayWithRequestPaymentResponse paymentResponse=null;
//        try {
//            String outTradeNo = "OTN"+GenerateCodeUtil.createCode(20);
//            // 根据token 查询对应用户openId
//            //String openId = "oV5OM5SM-_UH1bXnZqqHWWCf4tGA";
//
//            // 初始化商户配置
//            RSAConfig config =
//                    new RSAConfig.Builder()
//                            .merchantId(merchantId)
//                            // 使用 com.wechat.pay.java.core.util 中的函数从本地文件中加载商户私钥，商户私钥会用来生成请求的签名
//                            .privateKeyFromPath(privateKeyPath)
//                            .merchantSerialNumber(merchantSerialNumber)
//                            .wechatPayCertificatesFromPath(wechatPayCertificatePath)
//                            .build();
//            // 初始化服务
//            jsapiServiceExtension =
//                    new JsapiServiceExtension.Builder()
//                            .config(config)
//                            .signType("RSA")
//                            .build();
//
//            PrepayRequest prepayRequest = new PrepayRequest();
//            prepayRequest.setAppid(AppInfoEnum.APP_ID.getValue());
//            prepayRequest.setMchid(AppInfoEnum.MCH_ID.getValue());
//            prepayRequest.setDescription("商品描述");
//            prepayRequest.setOutTradeNo(outTradeNo);
//            // todo 回调地址
//            prepayRequest.setNotifyUrl("https://test.com");
//            Amount amount = new Amount();
//            amount.setTotal(param.getPayPrice().multiply(BigDecimal.valueOf(100)).intValue());
//            amount.setCurrency("CNY");
//            prepayRequest.setAmount(amount);
//            Payer payer = new Payer();
//            payer.setOpenid(openId);
//            prepayRequest.setPayer(payer);
//            // todo 订单有效时间或许是否需要待确认
//            // prepayRequest.setTimeExpire("");
//            paymentResponse = jsapiServiceExtension.prepayWithRequestPayment(prepayRequest);
//            log.info("预支付下单结果：{}",paymentResponse);
//            orderDO.setOrderCode(orderCode);
//            orderDO.setNum(param.getCrNum()+param.getEtNum());
//            orderDO.setPrice(param.getPayPrice());
//            orderDO.setProductCode(param.getProductCode());
//            orderDO.setProductName(param.getProductName());
//            orderDO.setStatus(OrderStatusEnum.WAIT_PAY.getStatus());
//            orderDO.setOutTradeNo(outTradeNo);
//            orderDO.setPrePayId(paymentResponse.getPackageVal());
//            //orderDO.setPrePayId("cspayId");
//            String touristIds = "";
//            for (int i=0;i<param.getTouristIds().size(); i++) {
//                if (i==0) {
//                    touristIds+=param.getTouristIds().get(i);
//                } else {
//                    touristIds+=","+param.getTouristIds().get(i);
//                }
//            }
//            orderDO.setTouristIds(touristIds);
//            orderDO.setOpenId(openId);
//        } catch (Exception e) {
//            log.error("订单创建错误");
//            e.printStackTrace();
//            return null;
//        }
//        orderDO.setCreateDate(new Date());
//        orderDO.setUpdateDate(orderDO.getCreateDate());
//        orderDO.insert();
//        CreateOrderReturnDTO returnDTO = new CreateOrderReturnDTO();
//        returnDTO.setOrderCode(orderCode);
//        returnDTO.setPackageVal(paymentResponse.getPackageVal());
//        returnDTO.setPaySign(paymentResponse.getPaySign());
//        returnDTO.setTimeStamp(paymentResponse.getTimeStamp());
//        returnDTO.setNonceStr(paymentResponse.getNonceStr());
//        returnDTO.setAppId(paymentResponse.getAppId());
//        returnDTO.setSignType(paymentResponse.getSignType());
////        returnDTO.setPackageVal("prepay='cs'");
////        returnDTO.setPaySign("sign");
////        returnDTO.setTimeStamp("2423231423");
////        returnDTO.setNonceStr("345fgser32rf3");
////        returnDTO.setAppId("appid231");
////        returnDTO.setSignType("SNY");
//        return returnDTO;
//    }

    @Override
    public void wxPayNotify(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public List<SelectOrderDTO> getOrderListWX(String openId) {
        try {
            List<SelectOrderDTO> selectOrderDTOS = new ArrayList<>();
            //List<OrderDO> orderDOS = list(Wrappers.<OrderDO>lambdaQuery().eq(OrderDO::getOpenId, openId));
            List<OrderDO> orderDOS = list(Wrappers.<OrderDO>lambdaQuery().orderByDesc(OrderDO::getCreateDate));
            for (OrderDO orderDO : orderDOS){
                SelectOrderDTO selectOrderDTO = new SelectOrderDTO();
                selectOrderDTO.setProductName(orderDO.getProductName());
                selectOrderDTO.setPayPrice(orderDO.getPrice());
                selectOrderDTO.setOrderStatus(orderDO.getStatus());
                selectOrderDTO.setOrderCode(orderDO.getOrderCode());
                selectOrderDTO.setNum(orderDO.getNum());
                selectOrderDTO.setCreateDate(orderDO.getCreateDate());
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
    public Page<SelectOrderDTO> getOrderList(SelOrderListParam param) {
        Page<OrderDO> page = new Page(param.getCurrent(),param.getSize());
        LambdaQueryWrapper<OrderDO> wrapper = Wrappers.<OrderDO>lambdaQuery()
                .eq(param.getDistributionIs()!=null,OrderDO::getDistributionIs,param.getDistributionIs())
                .eq(param.getFxsCode() != null,OrderDO::getFxsCode,param.getFxsCode())
                .eq(param.getOrderStatus()!=null,OrderDO::getStatus,param.getOrderStatus());
        Page<OrderDO> doPage = page(page,wrapper);
        Page<SelectOrderDTO> dtoPage = new Page<SelectOrderDTO>();
        return dtoPage;
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

//    @Override
//    public void updateFXS(String openId, String fxsCode) {
//
//    }

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
