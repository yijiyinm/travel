package com.example.travel.util;

import java.util.HashMap;

/**
 * @author yijiyin
 */
public class ResultCode extends HashMap {
    // 不允许其他类new创建
    private ResultCode(){}

    public ResultCode put(String key, Object value){
        super.put(key,value);
        return this;
    }

    public static ResultCode of(int code, String msg){
        return new ResultCode().put("code",code)
                .put("msg",msg);
    }
}
