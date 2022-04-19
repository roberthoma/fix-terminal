package com.fixterminal.shared.enumerators;

public enum RxOrderTimeInForce {
    DAY, //TimeInForce.DAY
    IOC, //TimeInForce.IMMEDIATE_OR_CANCEL
    OPG, //TimeInForce.AT_THE_OPENING
    GTC, //TimeInForce.GOOD_TILL_CANCEL
    GTX //TimeInForce.GOOD_TILL_CROSSING
}
