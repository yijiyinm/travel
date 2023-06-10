package com.example.travel.enums;

/**
 * @author chenying
 * @Description TODO
 * @Date 2023/5/7 21:59
 */
public enum LabelEnum {
    zby(1,"周边拼团游"),
    pc(2,"包车/拼车"),
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
