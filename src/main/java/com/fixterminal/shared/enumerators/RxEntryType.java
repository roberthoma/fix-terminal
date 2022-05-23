package com.fixterminal.shared.enumerators;

public enum RxEntryType {
    BID('0'),
    OFFER('1');

    char value;
    RxEntryType(char value){
        this.value = value;
    }
}
