package com.example.travel.util;

/**
 * @author yijiyin
 */

public enum AppInfoEnum {

    APP_ID("wx2e0c5d458b1896b5","小程序id"),
    APP_SECRET("6ead95f2fcd40471225aaa9083528914","小程序秘钥"),
    MCH_ID("","微信下发直连商户号");

    private String value;
    private String desc;

    private AppInfoEnum(String value,String desc) {
        this.value = value;
        this.desc=desc;
    }

    public String getValue(){
        return this.value;
    }

}
