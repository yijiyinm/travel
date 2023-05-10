package com.example.travel.util;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yijiyin
 */
@Data
public class BaseRespResult<T> implements Serializable {
    private static final long serialVersionUID = -4507574037830481719L;

    private static final String OK_CODE = "200";
    private static final String OK_MESSAGE = "操作成功";

    boolean success;
    private String type;
    private String code;
    private String errorCode;
    private String message;
    private String exceptionId;
    private T data;

    /**
     * 分页-数据
     */
    private List rows;
    /**
     * 分页-总条数
     */
    private Long totalCount;

    public BaseRespResult() {
    }


    public BaseRespResult(boolean success, T data, String code, String message, String errorCode, String exceptionId) {
        this.success = success;
        this.data = data;
        this.code = code;
        this.message = message;
        this.errorCode = errorCode;
        this.exceptionId = exceptionId;
    }

    public BaseRespResult(boolean success, String type, T data, String code, String message, String errorCode, String exceptionId) {
        this.success = success;
        this.type = type;
        this.data = data;
        this.code = code;
        this.message = message;
        this.errorCode = errorCode;
        this.exceptionId = exceptionId;
    }

    public BaseRespResult(List rows, T data, String message) {
        this.success = true;
        this.type = "";
        this.code = OK_CODE;
        this.rows = rows;
        this.data = data;
        this.message = message;
        this.errorCode = "";
        this.exceptionId = "";
    }


    public static BaseRespResult errorResult(String data) {
        return errorResult("500", data);
    }

    public static BaseRespResult errorResult(String code, String data) {
        return new BaseRespResult(false, data, code, data, "", "");
    }

    public static BaseRespResult errorResult(String code, String errorCode, String message) {
        return new BaseRespResult(false, message, code, message, errorCode, "");
    }

    public static BaseRespResult errorResult(String code, String errorCode, Object data) {
        return new BaseRespResult(false, data, code, null, errorCode, "");
    }

    public static BaseRespResult errorResult(String code, String errorCode, String data, String message) {
        return new BaseRespResult(false, data, code, message, errorCode, "");
    }

    public static BaseRespResult errorResult(String code, String errorCode, Object data, String message, String exceptionId) {
        return new BaseRespResult(false, data, code, message, errorCode, exceptionId);
    }

    public static BaseRespResult successResult(Object data) {
        return new BaseRespResult(true, data, OK_CODE, OK_MESSAGE, "", "");
    }

    public static BaseRespResult successResult(Object data, String message) {
        return new BaseRespResult(true, data, OK_CODE, message, "", "");
    }

    public static BaseRespResult successResult(Object data, String code, String message) {
        return new BaseRespResult(true, data, code, message, "", "");
    }

    public static BaseRespResult successResult(String type, Object data, String code, String message) {
        return new BaseRespResult(true, type, data, code, message, "", "");
    }


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
