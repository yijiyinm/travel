package com.example.travel;


import com.alibaba.fastjson.JSON;
import com.example.travel.util.AppInfoEnum;
import com.example.travel.util.GenerateCodeUtil;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.RSAConfig;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class TravelServiceApplicationTests {


    public static String merchantId = AppInfoEnum.MCH_ID.getValue();
    public static String privateKeyPath = "src/main/resources/apiclient_key.pem";
    public static String merchantSerialNumber = "39160C3B15E22A1444C9B8B6D10C33D2395CBD23";
    public static String wechatPayCertificatePath = "src/main/resources/wechatpay.pem";

    public static void main(String[] args) {
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
        JsapiServiceExtension jsapiServiceExtension =
                new JsapiServiceExtension.Builder()
                        .config(config)
                        .signType("RSA")
                        .build();
        com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest prepayRequest = new PrepayRequest();
        prepayRequest.setAppid(AppInfoEnum.APP_ID.getValue());
        prepayRequest.setMchid(merchantId);
        prepayRequest.setDescription("商品描述");
        String outTradeNo = "OTN"+ GenerateCodeUtil.createCode(20);

        prepayRequest.setOutTradeNo(outTradeNo);
        // todo 回调地址
        prepayRequest.setNotifyUrl("https://www.cloudroc.top/product/getProductDetail");
        com.wechat.pay.java.service.payments.jsapi.model.Amount amount = new Amount();
        amount.setTotal(1);
        amount.setCurrency("CNY");
        prepayRequest.setAmount(amount);
        Payer payer = new Payer();
        payer.setOpenid("o6U8L5MZe6-rflFrybXXAkwak3D8");
        prepayRequest.setPayer(payer);
        // todo 订单有效时间或许是否需要待确认
        // prepayRequest.setTimeExpire("");
        PrepayWithRequestPaymentResponse prepayWithRequestPaymentResponse = jsapiServiceExtension.prepayWithRequestPayment(prepayRequest);
        System.out.println("prepayWithRequestPaymentResponse:"+ JSON.toJSONString(prepayWithRequestPaymentResponse));
    }
}
