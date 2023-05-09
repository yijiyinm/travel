package com.example.travel.enums;

/**
 * @author yijiyin
 */

public enum OrderStatusEnum {

    WAIT_PAY(1,"待支付,待审核,草稿"),
    ALREADY_PAY(2,"支付成功,审核通过,上架"),
    FAILURE_PAY(3,"支付失败,审核不通过,下架"),
    DELETE_STATUS(4,"删除");

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
