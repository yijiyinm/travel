package com.example.travel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.travel.dao.OrderMapper;
import com.example.travel.dao.entity.ProductPriceDO;
import com.example.travel.dao.entity.UserDO;
import com.example.travel.dto.*;
import com.example.travel.dao.entity.OrderDO;
import com.example.travel.enums.FxsJsEnum;
import com.example.travel.enums.OrderStatusEnum;
import com.example.travel.param.SelOrderListParam;
import com.example.travel.service.OrderService;
import com.example.travel.service.ProductPriceService;
import com.example.travel.service.TouristService;
import com.example.travel.service.ProductService;
import com.example.travel.util.AppInfoEnum;
import com.example.travel.util.GenerateCodeUtil;
import com.google.common.collect.Lists;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.cipher.Signer;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.core.util.NonceUtil;
import com.wechat.pay.java.service.partnerpayments.jsapi.model.Transaction;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.*;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.refund.RefundService;
import com.wechat.pay.java.service.refund.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
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
    @Resource
    private UserService userService;
    @Autowired
    private RSAAutoCertificateConfig rsaAutoCertificateConfig;
    @Autowired
    private ProductPriceService productPriceService;

    public static String merchantId = AppInfoEnum.MCH_ID.getValue();
    // public static String wechatPayCertificatePath = "src/main/resources/apiclient_cert.pem";
    public static JsapiServiceExtension jsapiServiceExtension;
    public static RefundService refundService;




    @Override
    public CreateOrderReturnDTO createOrder(CreateOrderDTO param,String openId){
        log.info("创建订单入参:{}",param);
        CreateOrderReturnDTO returnDTO = new CreateOrderReturnDTO();


        OrderDO orderDO = new OrderDO();
        String orderCode = "DD"+GenerateCodeUtil.createCode(18);
        PrepayWithRequestPaymentResponse paymentResponse=null;
        try {
            Integer num = this.getDaySumByProductCode(param.getProductCode(),new SimpleDateFormat("yyyy-MM-dd").parse(param.getChuXingDate()));
            log.info("查询出的已出单数量{}",num);
            ProductPriceDO productPriceDO =productPriceService.getProductDayDetail(param.getProductCode(),param.getChuXingDate());
            log.info("查询出的对应商品价格库存信息{}",productPriceDO);
            Integer inventoryLeftover = productPriceDO.getInventory()-num;
            if (inventoryLeftover <= 0) {
                returnDTO.setErrorRemark("库存不足");
                return returnDTO;
            }

              String outTradeNo = "OTN"+GenerateCodeUtil.createCode(10);


            // 初始化服务
            jsapiServiceExtension =
                    new JsapiServiceExtension.Builder()
                            .config(rsaAutoCertificateConfig)
                            .signType("RSA")
                            .build();

            PrepayRequest prepayRequest = new PrepayRequest();
            prepayRequest.setAppid(AppInfoEnum.APP_ID.getValue());
            prepayRequest.setMchid(merchantId);
            prepayRequest.setDescription("商品描述");
            prepayRequest.setOutTradeNo(outTradeNo);
            // 回调地址
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
            orderDO.setChuXingDate(param.getChuXingDate());
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
            WxUserDTO wxUserDTO = userService.getUserInfo(openId);

            if (StringUtils.isNotEmpty(wxUserDTO.getBindfxsCode())) {
                orderDO.setFxsCode(wxUserDTO.getBindfxsCode());
                orderDO.setDistributionIs(Boolean.TRUE);

                // 查询分销商手机号
                UserDO userDO = userService.getUserInfoByFxsCode(wxUserDTO.getBindfxsCode());
                if (userDO != null){
                    orderDO.setFxsPhone(userDO.getPhone());
                }
            }
        } catch (Exception e) {
            log.error("订单创建错误:{}",e);
            e.printStackTrace();
            return null;
        }
        orderDO.setCreateDate(new Date());
        orderDO.setUpdateDate(orderDO.getCreateDate());
        orderDO.insert();
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
    public CreateOrderReturnDTO payOrder(String orderCode) {
        OrderDO orderDO = getOne(Wrappers.<OrderDO>lambdaQuery().eq(OrderDO::getOrderCode, orderCode).eq(OrderDO::getStatus,OrderStatusEnum.WAIT_PAY.getStatus()));
        if (orderDO != null) {

            long timestamp = Instant.now().getEpochSecond();
            String nonceStr = NonceUtil.createNonce(32);
            String message =
                    AppInfoEnum.APP_ID.getValue() + "\n" + timestamp + "\n" + nonceStr + "\n" + orderDO.getPrePayId() + "\n";
            String sign = rsaAutoCertificateConfig.createSigner().sign(message).getSign();
            CreateOrderReturnDTO response = new CreateOrderReturnDTO();
            response.setAppId(AppInfoEnum.APP_ID.getValue());
            response.setTimeStamp(String.valueOf(timestamp));
            response.setNonceStr(nonceStr);
            response.setPackageVal(orderDO.getPrePayId());
            response.setSignType("RSA");
            response.setPaySign(sign);
            return response;
        }
        return null;
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
    public void wxPayReturnNotify(HttpServletRequest request, HttpServletResponse response) {
        RefundNotification refundNotification = getWxReturnNotifyParamMap(request);
        log.info("退款结果:{}",refundNotification);
        if (refundNotification != null) {

            OrderDO orderDO = null;
            try {
                orderDO = getOne(Wrappers.<OrderDO>lambdaQuery().eq(OrderDO::getReturnCode, refundNotification.getOutRefundNo()));
                log.info("用户信息:{}",orderDO);
            } catch (Exception e) {
                log.error("查询商户订单异常:{}",e);
                e.printStackTrace();
                return;
            }

            String success = "SUCCESS";
            if (Status.SUCCESS.equals(refundNotification.getRefundStatus())) {
                // 退款成功
                //Double sum = 100D;
                //DecimalFormat df = new DecimalFormat("0.00");
                //String price = df.format(refundNotification.getAmount().getRefund()/sum);

                BigDecimal returnAmount = orderDO.getRefundAmount().add(orderDO.getThisRefundAmount());
                orderDO.setRefundAmount(returnAmount);
                if (orderDO.getPrice().compareTo(returnAmount) == 1) {
                    orderDO.setStatus(OrderStatusEnum.PART_RETURN.getStatus());
                } else {
                    orderDO.setStatus(OrderStatusEnum.DELETE_STATUS.getStatus());
                }
                orderDO.setThisRefundAmount(null);
            }else {
                // 退款失败
                orderDO.setStatus(OrderStatusEnum.PART_RETURN_ERROR.getStatus());
            }
            orderDO.updateById();

        }
    }

    @Override
    public List<SelectOrderDTO> getOrderListWX(String openId,SelectOrderDTO selectOrderDT) {
        log.info("小程序获取订单用户openId:{}"+openId);
        try {
            List<SelectOrderDTO> selectOrderDTOS = new ArrayList<>();
            LambdaQueryWrapper<OrderDO> eq =  Wrappers.<OrderDO>lambdaQuery().eq(OrderDO::getOpenId, openId);
            if(!Objects.isNull(selectOrderDT.getOrderStatus())){
                eq.in(OrderDO::getStatus,selectOrderDT.getOrderStatus());
            }

            List<OrderDO> orderDOS = list(eq.orderByDesc(OrderDO::getCreateDate));
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
                selectOrderDTO.setChuXingDate(orderDO.getChuXingDate());
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
    public List<SelectOrderDTO> getFxsOrderListWX(String fxsCode) {
        log.info("小程序获取分销商订单分销商fxsCode:{}"+fxsCode);
        try {
            List<SelectOrderDTO> selectOrderDTOS = new ArrayList<>();
            List<OrderDO> orderDOS = list(Wrappers.<OrderDO>lambdaQuery().eq(OrderDO::getFxsCode, fxsCode).ne(OrderDO::getStatus,1).orderByDesc(OrderDO::getCreateDate));
            //List<OrderDO> orderDOS = list(Wrappers.<OrderDO>lambdaQuery().orderByDesc(OrderDO::getCreateDate));
            for (OrderDO orderDO : orderDOS){
                SelectOrderDTO selectOrderDTO = new SelectOrderDTO();
                selectOrderDTO.setProductName(orderDO.getProductName());
                selectOrderDTO.setPayPrice(orderDO.getPrice());
                selectOrderDTO.setOrderStatus(orderDO.getStatus());
                selectOrderDTO.setOrderCode(orderDO.getOrderCode());
                selectOrderDTO.setNum(orderDO.getNum());
                selectOrderDTO.setCreateDate(orderDO.getCreateDate());
                selectOrderDTO.setFxsJs(orderDO.getFxsJs());
                selectOrderDTO.setFxsJsName(FxsJsEnum.getNameByCode(orderDO.getFxsJs()));
                // 产品信息
                AddProductDTO addProductDTO = productService.getProductDetail(orderDO.getProductCode());
                selectOrderDTO.setMainUrl(addProductDTO.getMainUrl());
                selectOrderDTO.setDescription(addProductDTO.getDescription());
                selectOrderDTO.setChuXingDate(orderDO.getChuXingDate());
                // 出行人 取游客第一个人
                // 游客信息
                String[] touristList = orderDO.getTouristIds().split(",");
                TouristDTO touristDTO = touristService.getTouristById(Long.valueOf(touristList[0]));
                if (touristDTO != null) {
                    selectOrderDTO.setPedestrianName(touristDTO.getName());
                }

                selectOrderDTOS.add(selectOrderDTO);
            }
            return selectOrderDTOS;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("小程序获取分销商订单列表失败");
        }
        return null;
    }


    @Override
    public Page<SelectOrderDTO> getOrderList(SelOrderListParam param) {
        try {
            Page<OrderDO> page = new Page(param.getCurrent(),param.getSize());
            LambdaQueryWrapper<OrderDO> wrapper = Wrappers.<OrderDO>lambdaQuery()
                    .eq(param.getDistributionIs()!=null,OrderDO::getDistributionIs,param.getDistributionIs())
                    .eq(StringUtils.isNotEmpty(param.getFxsCode()),OrderDO::getFxsCode,param.getFxsCode())
                    .eq(StringUtils.isNotEmpty(param.getFxsPhone()),OrderDO::getFxsPhone,param.getFxsPhone())
                    .eq(param.getOrderStatus()!=null,OrderDO::getStatus,param.getOrderStatus())
                    .eq(param.getFxsJs()!=null,OrderDO::getFxsJs,param.getFxsJs())
                    .eq(StringUtils.isNotEmpty(param.getOrderCode()),OrderDO::getOrderCode,param.getOrderCode()).orderByDesc(OrderDO::getCreateDate);
            if (StringUtils.isNotEmpty(param.getMonth())) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(param.getMonth()+"-01");
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                calendar.set(Calendar.DAY_OF_MONTH,1);
                String firstDay = sdf.format(calendar.getTime());
                log.info("第一天:"+firstDay);
                calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                String lastDay = sdf.format(calendar.getTime());
                log.info("最后一天："+lastDay);
                wrapper.between(OrderDO::getCreateDate,firstDay+" 00:00:00",lastDay+" 23:59:59");
            }
            //.between();
            Page<OrderDO> doPage = page(page,wrapper);
            Page<SelectOrderDTO> dtoPage = new Page<>();
            log.info("订单分页数据：{}",doPage.getTotal());



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
                selectOrderDTO.setFxsPhone(orderDO.getFxsPhone());
                selectOrderDTO.setCreateDate(orderDO.getCreateDate());
                selectOrderDTO.setChuXingDate(orderDO.getChuXingDate());
                selectOrderDTO.setRefundAmount(orderDO.getRefundAmount());
                selectOrderDTO.setReturnCode(orderDO.getReturnCode());
                selectOrderDTO.setThisRefundAmount(orderDO.getThisRefundAmount());

                selectOrderDTO.setFxsJs(orderDO.getFxsJs());
                selectOrderDTO.setFxsJsName(FxsJsEnum.getNameByCode(orderDO.getFxsJs()));
                selectOrderDTOS.add(selectOrderDTO);
            }
            dtoPage.setRecords(selectOrderDTOS);
            return dtoPage;
        } catch (Exception e) {
            log.error("查询订单列表异常:{}",e);
            e.printStackTrace();
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
            selectOrderDTO.setChuXingDate(orderDO.getChuXingDate());
            selectOrderDTO.setRefundAmount(orderDO.getRefundAmount());
            selectOrderDTO.setReturnCode(orderDO.getReturnCode());
            selectOrderDTO.setThisRefundAmount(orderDO.getThisRefundAmount());
            selectOrderDTO.setOrderCode(orderDO.getOrderCode());

            selectOrderDTO.setFxsJs(orderDO.getFxsJs());
            selectOrderDTO.setFxsJsName(FxsJsEnum.getNameByCode(orderDO.getFxsJs()));
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
        } catch (Exception e) {
            log.error("查询订单详情错误");
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public Boolean orderRefund(String orderCode,BigDecimal refundAmount) {

        try {
            OrderDO orderDO = getOne(Wrappers.<OrderDO>lambdaQuery().eq(OrderDO::getOrderCode, orderCode));
            // 验证退款金额
            //加上本次退款金额验证是否超过订单总金额
            BigDecimal refundPrice = orderDO.getRefundAmount().add(refundAmount);
            int compare = refundPrice.compareTo(orderDO.getPrice());
            if (compare == 1) {
                log.error("退款金额超过最大订单金额");
                return false;
            }

            if (OrderStatusEnum.ALREADY_PAY.getStatus().equals(orderDO.getStatus()) || OrderStatusEnum.PART_RETURN.getStatus().equals(orderDO.getStatus())){

                // 退款单号生成
                String returnCode = "TK"+GenerateCodeUtil.createCode(12);

                // 退款接口调用

                // 初始化服务
                refundService =
                        new RefundService.Builder()
                                .config(rsaAutoCertificateConfig)
                                .build();
                CreateRequest createRequest = new CreateRequest();
                createRequest.setOutTradeNo(orderDO.getOutTradeNo());
                createRequest.setOutRefundNo(returnCode);
                createRequest.setNotifyUrl("https://www.cloudroc.top/notify/wxPay/return/notify");

                AmountReq amountReq = new AmountReq();
                amountReq.setTotal(orderDO.getPrice().multiply(BigDecimal.valueOf(100)).longValue());
                amountReq.setRefund(refundAmount.multiply(BigDecimal.valueOf(100)).longValue());
                amountReq.setCurrency("CNY");
                createRequest.setAmount(amountReq);
                Refund refund = refundService.create(createRequest);
                if (refund != null || StringUtils.isNotEmpty(refund.getRefundId())){
                    orderDO.setStatus(OrderStatusEnum.PART_RETURN_PROCESS.getStatus());
                    orderDO.setReturnCode(returnCode);
                    orderDO.setThisRefundAmount(refundAmount);
                    //orderDO.setRefundAmount(refundAmount);
                    return orderDO.updateById();
                } else {
                    log.error("发起退款申请出现异常");
                    return false;
                }
            } else {
                log.info("只有支付完成或部分退款订单才能退款！");
                return false;
            }
        } catch (Exception e) {
            log.error("退款异常：{}",e);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Integer getDaySumByProductCode(String productCode, Date dayDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(dayDate);
        Integer num = baseMapper.getDaySumByProductCode(productCode,dateStr);
        if (num == null)  {
            return 0;
        }
        return num;
    }

    @Override
    public List<OrderDO> getAllOrder(SelectOrderDTO selectOrderDT) {
        try {
            List<SelectOrderDTO> selectOrderDTOS = new ArrayList<>();
            LambdaQueryWrapper<OrderDO> orderDOLambdaQueryWrapper = Wrappers.<OrderDO>lambdaQuery();
            if(!Objects.isNull(selectOrderDT.getOrderStatus())){
                orderDOLambdaQueryWrapper.in(OrderDO::getStatus,selectOrderDT.getOrderStatus());
            }

            List<OrderDO> orderDOS = list(orderDOLambdaQueryWrapper.orderByDesc(OrderDO::getCreateDate));

            return orderDOS;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getAllOrder");
        }
        return Lists.newArrayList();
    }

    @Override
    public Boolean fsxJsByOrderCode(String orderCodes) {
        List<String> list = Arrays.asList(orderCodes.split(","));
        for (String orderCode:list){
            OrderDO orderDO = getOne(Wrappers.<OrderDO>lambdaQuery().eq(OrderDO::getOrderCode, orderCode));
            if(orderDO.getFxsJs().equals(2)){
                orderDO.setFxsJs(FxsJsEnum.yjs.getDex());
                orderDO.updateById();
            }
        }


        return true;
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
            NotificationParser parser = new NotificationParser(rsaAutoCertificateConfig);

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

    /**
     * desc 获取微信支付回调请求参数
     *
     */
    private RefundNotification getWxReturnNotifyParamMap(HttpServletRequest request) {


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
            NotificationParser parser = new NotificationParser(rsaAutoCertificateConfig);

            log.info("body信息：{}",wholeStr);
            RefundNotification transaction = parser.parse(requestParam, RefundNotification.class);
            return transaction;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("退款通知回调异常");
            return null;
        }
    }
}
