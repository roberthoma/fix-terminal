package com.fixterminal.market.business.parameters;

import com.fixterminal.shared.parameters.RxParameterDesc;

public enum RxTradeParameter implements RxParameterDesc {

    AUTO_TRADE("Is Auto trading on off"),
    BREAKEVEN_ACTIVATE_DIST("Distance of activating Breakevent"),
    //BREAKEVEN_PROFIT;


    ORDER_TYPE("Order type : MARKET LIMIT etc");

//    private RxOrderType type;


    final String description;
//    final Class clazz;


//    private RxInstrument instrument;
//    private Double quantity;
//    private RxOrderType type;
//    private RxOrderTimeInForce tif;
//    private Double price;
//
//    // Parameters configurable from file
//
//    //    private RxOnOff AUTO_TRADE;
////    private RxOnOff AUTO_BREAKEVEN;
////    private double  BREAKEVEN_ACTIVATE_DIST;
//    private double  BREAKEVEN_PROFIT;
//    private double  STOP_LOSS_DISTANCE;
//    private double  QUANTITY;
//    //  private double  MOVE_DISTANCE;




//    New Order params
//    SL_ON_OPEN : 2pips
//    TP_ON_OPEN : 5pips
//    AUTO_SL : ON/OFF

    RxTradeParameter(String description){
      this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getSymbol() {
        return this.toString();
    }
}
