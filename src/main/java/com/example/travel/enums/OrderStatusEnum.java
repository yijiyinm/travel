package com.example.travel.enums;

/**
 * @author yijiyin
 */

public enum OrderStatusEnum {

    WAIT_PAY(1,"待支付,待审核,草稿"),
    ALREADY_PAY(2,"支付成功,审核通过,上架"),
    FAILURE_PAY(3,"支付失败,审核不通过,下架"),
    DELETE_STATUS(4,"已全部退款,删除"),
    PART_RETURN(5,"部分退款"),
    PART_RETURN_PROCESS(6,"退款申请中"),
    PART_RETURN_ERROR(7,"退款失败");

    private final Integer status;
    private final String desc;

    OrderStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc=desc;
    }

    public Integer getStatus(){
        return this.status;
    }

}
