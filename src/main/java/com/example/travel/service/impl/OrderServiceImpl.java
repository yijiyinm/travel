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
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.partnerpayments.jsapi.model.Transaction;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public static String privateKeyPath = "./apiclient_key.pem";
    public static String merchantSerialNumber = AppInfoEnum.MERCHANT_SERIAL_NUMBER.getValue();
    // public static String wechatPayCertificatePath = "src/main/resources/apiclient_cert.pem";
    public static JsapiServiceExtension jsapiServiceExtension;

    Config config = new RSAAutoCertificateConfig.Builder()
            .merchantId(merchantId)
            .privateKeyFromPath("./apiclient_key.pem")
            .merchantSerialNumber(merchantSerialNumber)
            .apiV3Key("18099980588188099809932233566607")
            .build();

    @Override
    public CreateOrderReturnDTO createOrder(CreateOrderDTO param,String openId){

        OrderDO orderDO = new OrderDO();
        String orderCode = "DD"+GenerateCodeUtil.createCode(18);
        PrepayWithRequestPaymentResponse paymentResponse=null;
        try {
              String outTradeNo = "OTN"+GenerateCodeUtil.createCode(10);


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
            prepayRequest.setNotifyUrl("https://www.cloudroc.top/notify/wxPay/notify");
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
            orderDO.setFxsCode(param.getFxsCode());
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

    @Override
    public void wxPayNotify(HttpServletRequest request, HttpServletResponse response) {
        Transaction transaction = getWxNotifyParamMap(request);
        log.info("支付回调结果:"+transaction);
        if (transaction != null){
            OrderDO orderDO = null;
            try {
                 orderDO = getOne(Wrappers.<OrderDO>lambdaQuery().eq(OrderDO::getOutTradeNo, transaction.getOutTradeNo()));
                 log.info("用户信息:{}",orderDO);
            } catch (Exception e) {
                log.error("查询商户订单异常:{}",e);
                e.printStackTrace();
                return;
            }
            log.info("状态值：{}",transaction.getTradeState());
            log.info("状态值2：{}",transaction.getTradeState().name());
            if ("SUCCESS".equals(transaction.getTradeState().name())) {
                // 支付成功 通过商户订单号同步状态
                if (!OrderStatusEnum.ALREADY_PAY.getStatus().equals(orderDO.getStatus())) {
                    orderDO.setStatus(OrderStatusEnum.ALREADY_PAY.getStatus());
                }
            }else{
                orderDO.setStatus(OrderStatusEnum.FAILURE_PAY.getStatus());
            }

            log.info("更新数据:{}",orderDO);
            orderDO.updateById();
        }
    }

    @Override
    public List<SelectOrderDTO> getOrderListWX(String openId) {
        log.info("小程序获取订单用户openId:{}"+openId);
        try {
            List<SelectOrderDTO> selectOrderDTOS = new ArrayList<>();
            List<OrderDO> orderDOS = list(Wrappers.<OrderDO>lambdaQuery().eq(OrderDO::getOpenId, openId).orderByDesc(OrderDO::getCreateDate));
            //List<OrderDO> orderDOS = list(Wrappers.<OrderDO>lambdaQuery().orderByDesc(OrderDO::getCreateDate));
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
                //.between();
        Page<OrderDO> doPage = page(page,wrapper);
        Page<SelectOrderDTO> dtoPage = new Page<>();
        log.info("订单分页数据：{}",doPage.getTotal());
        log.info("订单分页数据：{}",doPage.getSize());
        log.info("订单分页数据：{}",doPage.getRecords());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        String firstDay = sdf.format(calendar.getTime());
        System.out.println("第一天:"+firstDay);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String lastDay = sdf.format(calendar.getTime());
        System.out.println("最后一天："+lastDay);

        dtoPage.setSize(doPage.getSize());
        dtoPage.setTotal(doPage.getTotal());
        dtoPage.setCurrent(doPage.getCurrent());
        List<SelectOrderDTO> selectOrderDTOS = new ArrayList<>();
        for (OrderDO orderDO :doPage.getRecords()){
            SelectOrderDTO selectOrderDTO = new SelectOrderDTO();
            selectOrderDTO.setOrderStatus(orderDO.getStatus());
            selectOrderDTO.setPayPrice(orderDO.getPrice());
            selectOrderDTO.setNum(orderDO.getNum());
            selectOrderDTO.setProductName(orderDO.getProductName());
            selectOrderDTO.setOrderCode(orderDO.getOrderCode());
            selectOrderDTO.setFxsCode(orderDO.getFxsCode());
            selectOrderDTOS.add(selectOrderDTO);
        }
        dtoPage.setRecords(selectOrderDTOS);
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

    /**
     * desc 获取微信支付回调请求参数
     *
     */
    private Transaction getWxNotifyParamMap(HttpServletRequest request) {

        try {
            BufferedReader br = request.getReader();
            String str  = "";
            String wholeStr = "";
            while((str = br.readLine()) != null){
                wholeStr += str;
            }

            // 构造 RequestParam
            RequestParam requestParam = new RequestParam.Builder()
                    .serialNumber(request.getHeader("Wechatpay-Serial"))
                    .nonce(request.getHeader("Wechatpay-Nonce"))
                    .signature(request.getHeader("Wechatpay-Signature"))
                    .timestamp(request.getHeader("Wechatpay-Timestamp"))
                    .signType(request.getHeader("Wechatpay-Signature-Type"))
                    .body(wholeStr)
                    .build();

            // 如果已经初始化了 RSAAutoCertificateConfig，可直接使用

            // 初始化 NotificationParser
            NotificationParser parser = new NotificationParser((NotificationConfig) config);

            log.info("body信息：{}",wholeStr);
            // 以支付通知回调为例，验签、解密并转换成 Transaction
            Transaction transaction = parser.parse(requestParam, Transaction.class);
            return transaction;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("支付回调异常");
            return null;
        }
    }
}
