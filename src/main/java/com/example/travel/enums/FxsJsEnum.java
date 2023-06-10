package com.example.travel.enums;

/**
 * @author chenying
 * @Description TODO
 * @Date 2023/6/10 18:42
 */
public enum FxsJsEnum {
    yjs(1,"已结算"),
    wjs(2,"未结算"),
    ;

    private final Integer dex;
    private final String name;

    FxsJsEnum(Integer dex, String name) {
        this.dex = dex;
        this.name=name;
    }

    public Integer getDex() {
        return dex;
    }

    public String getName() {
        return name;
    }

    public static String getNameByCode(Integer code){
        for (FxsJsEnum fxsJsEnum:FxsJsEnum.values()){
            if(fxsJsEnum.dex.equals(code)){
                return fxsJsEnum.getName();
            }
        }
        return null;

    }
}
