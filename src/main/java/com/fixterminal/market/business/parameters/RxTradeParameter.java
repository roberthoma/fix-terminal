package com.fixterminal.market.business.parameters;

import com.fixterminal.shared.parameters.RxParameterDesc;

public enum RxTradeParameter implements RxParameterDesc {

    AUTO_TRADE("Is Auto trading on off"),
    AUTO_BREAKEVEN("Is Auto breakevent on off"),
    BREAKEVEN_ACTIVATE_DIST("Distance of activating Breakevent"),
    BREAKEVEN_PROFIT("Breakeven profit "),
    STOP_LOSS_DISTANCE("Stop loss distance on open position"),
    QUANTITY("Trade Quantity"),
    ORDER_TYPE("Order type : MARKET LIMIT etc");

    final String description;

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
