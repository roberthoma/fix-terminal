package com.fixterminal.market.business.parameters;

import com.fixterminal.shared.parameters.RxParameterDesc;

public enum RxTradeParameterType implements RxParameterDesc {

    INSTRUMENT("instrument","Instrument"),
    AUTO_TRADE("auto_trade","Is Auto trading on off"),
    AUTO_BREAKEVEN("auto_bre","Is Auto breakevent on off"),
    BREAKEVEN_ACTIVATE_DIST("bre_act_dist","Distance of activating Breakevent"),
    BREAKEVEN_PROFIT("bre_profit","Breakeven profit "),
    STOP_LOSS_DISTANCE("sl_dist","Stop loss distance on open position"),
    QUANTITY("quantity","Trade Quantity"),
    ORDER_TYPE("ord_type","Order type : MARKET LIMIT etc"),


    AUTO_TRAILING("auto_trls","Is auto trailing stop loss On OFF"),
    TRAILING_STOP_ACTIVATE_DIST("trls_act_dist","Trailing stop loss activate distance "),
    TRAILING_STOP_FOLLOW_DIST("trls_follow_dist","Trailing stop loss follow distance"),
    MARKET_DATA_REQUEST("m_data_req", " Send market data request" );


    final String description;
    final String shortName;

    RxTradeParameterType(String shortName,String description){
      this.shortName = shortName;
      this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getSymbol() {
        return this.name();
    }

    @Override
    public String getCmdSymbol() {
        return shortName;
    }
}
