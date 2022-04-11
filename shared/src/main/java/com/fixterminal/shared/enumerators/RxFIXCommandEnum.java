package com.fixterminal.shared.enumerators;

public enum RxFIXCommandEnum {
    BUY_MARKET("buy_market"),
    SELL_MARKET("sell_market"),
    CANCEL_ALL_ORDER("cancel_all_order"),
    LOGON("logon"),
    DATA("data"),
    LOGOUT("logout"),
    SET("set"),
    HELP("help"),
    INFO("info"),
    START_MONITOR("monitor"),
    ABOUT("about"),
    REQ_ORD_STATUS("OrderStatusRequest request_for_pos"),
    CLOSE("close");

    String cmdSymbol;

    RxFIXCommandEnum(String cmdSymbol){
        this.cmdSymbol = cmdSymbol;
    }
    public String getSymbol(){
        return cmdSymbol;
    };

}
