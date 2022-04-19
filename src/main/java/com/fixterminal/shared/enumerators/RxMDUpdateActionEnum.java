package com.fixterminal.shared.enumerators;

/*
* MarketDataUpdateAction
* */
public enum RxMDUpdateActionEnum {
    NEW('0'),
    CHANGE('1'),
    DELETE('2');

    char value;
    RxMDUpdateActionEnum(char value){
        this.value = value;
    }

    public static RxMDUpdateActionEnum of(char id) {
        for(RxMDUpdateActionEnum e : values()) {
            if(e.value == id) return e;
        }
        return null;
    }
}

