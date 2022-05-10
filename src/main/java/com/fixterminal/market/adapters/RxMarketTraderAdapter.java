package com.fixterminal.market.adapters;

import com.fixterminal.app.ports.RxMarketTraderPort;
import com.fixterminal.market.business.parameters.RxTradeParameters;
import com.fixterminal.market.business.parameters.RxTradeParametersDesk;
import com.fixterminal.market.business.trader.actions.RxTradeActions;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.parameters.RxParemetersMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RxMarketTraderAdapter implements RxMarketTraderPort {

    RxTradeActions actions;
    RxTradeParametersDesk parametersDesk;

    @Autowired
    RxMarketTraderAdapter(RxTradeActions actions, RxTradeParametersDesk parametersDesk){
        this.actions = actions;
        this.parametersDesk = parametersDesk;
    }

    @Override
    public void actionBuyMarket() {
        actions.actionBuyMarket();
    }

    @Override
    public void actionSellMarket() {
        actions.actionSellMarket();
    }

    @Override
    public void actionReverse() {

    }

    @Override
    public void actionClosePosition() {
        actions.actionClosePosition();
    }

    @Override
    public RxParemetersMap getParametersMap(RxInstrument instrument) {
        return parametersDesk.getTradeParameters(instrument)
                             .getParameterMap();
    }
}
