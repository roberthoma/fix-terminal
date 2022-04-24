package com.fixterminal.shared.enumerators;

public enum  RxOnOff {

    ON(true),
    OFF(false);

    private boolean booleanValue;

    RxOnOff(boolean booleanValue){
       this.booleanValue = booleanValue;
    }


}
