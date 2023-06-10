package com.example.travel.enums;

/**
 * @author chenying
 * @Description TODO
 * @Date 2023/5/7 21:59
 */
public enum LabelEnum {
    zby("1","周边拼团游"),
    pc("2","包车/拼车"),
    ;

    private final String dex;
    private final String name;

    LabelEnum(String dex, String name) {
        this.dex = dex;
        this.name=name;
    }

    public String getDex() {
        return dex;
    }

    public String getName() {
        return name;
    }

    public static String getNameByCode(String code){
        for (LabelEnum l :LabelEnum.values()){
            if(l.dex.equals(code)){
                return l.name;
            }
        }
        return null;

    }}
