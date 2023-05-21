package com.example.travel.enums;

/**
 * @author chenying
 * @Description TODO
 * @Date 2023/5/7 21:59
 */
public enum LabelEnum {
    zby(1,"周边游"),
    pc(2,"拼车"),
    sjt(3,"私家团"),
    mstj(4,"美食推荐"),
    zjgl(5,"自家攻略"),
    ;

    private final Integer dex;
    private final String name;

    LabelEnum(Integer dex, String name) {
        this.dex = dex;
        this.name=name;
    }

    public Integer getDex() {
        return dex;
    }

    public String getName() {
        return name;
    }
}
