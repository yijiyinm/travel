package com.example.travel.util;

/**
 * @author yijiyin
 */

public enum AppInfoEnum {

    APP_ID("wx051235e19ab2ae28","小程序id"),
    APP_SECRET("598aec2dc7903f65c27e8960b45e7bb7","小程序秘钥"),
    MCH_ID("1641830040","微信下发直连商户号");

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
