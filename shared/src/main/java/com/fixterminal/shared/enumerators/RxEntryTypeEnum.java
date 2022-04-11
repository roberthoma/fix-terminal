package com.fixterminal.shared.enumerators;

public enum RxEntryTypeEnum {
    BID('0'),
    OFFER('1');

    char value;
    RxEntryTypeEnum(char value){
        this.value = value;
    }
}
