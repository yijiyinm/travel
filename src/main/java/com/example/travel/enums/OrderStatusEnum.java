package com.example.travel.enums;

/**
 * @author yijiyin
 */

public enum OrderStatusEnum {

    WAIT_PAY(1,"待支付"),
    ALREADY_PAY(2,"支付成功"),
    FAILURE_PAY(3,"支付失败");

    private Integer status;
    private String desc;

    private OrderStatusEnum(Integer status,String desc) {
        this.status = status;
        this.desc=desc;
    }

    public Integer getStatus(){
        return this.status;
    }

}
