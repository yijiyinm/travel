package com.example.travel.beanHelp;

import com.example.travel.util.AppInfoEnum;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.io.File;

/**
 * @author chenying
 * @Description TODO
 * @Date 2023/5/15 11:31
 */
@Configuration
public class BeanHelp {

    public static String merchantId = AppInfoEnum.MCH_ID.getValue();

    public static String privateKeyPath = "apiclient_key.pem";
    public static String apiV3Key = "18099980588188099809932233566607";
    public static String merchantSerialNumber = AppInfoEnum.MERCHANT_SERIAL_NUMBER.getValue();

    @Lazy
    @Bean
    public RSAAutoCertificateConfig getRSAAutoCertificateConfig(){
        String path = System.getProperty("user.dir");
        return new RSAAutoCertificateConfig.Builder()
                .merchantId(merchantId)
                .privateKeyFromPath(path+ File.separator+privateKeyPath)
                .merchantSerialNumber(merchantSerialNumber)
                .apiV3Key(apiV3Key)
                .build();
    }
}
