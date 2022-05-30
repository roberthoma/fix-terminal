package com.fixterminal.market.adapters;

import com.fixterminal.app.ports.RxMarketTraderPort;
import com.fixterminal.market.business.parameters.RxTradeParametersDesk;
import com.fixterminal.market.business.trade.actions.RxActions;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.parameters.RxParemetersMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RxMarketTraderAdapter implements RxMarketTraderPort {

    RxActions actions;
    RxTradeParametersDesk parametersDesk;

    @Autowired
    RxMarketTraderAdapter(RxActions actions, RxTradeParametersDesk parametersDesk){
        this.actions = actions;
        this.parametersDesk = parametersDesk;
    }

    @Override
    public void actionBuyMarket(Map<String, String> params) {
        actions.actionBuyMarket(params);
    }

    @Override
    public void actionSellMarket(Map<String, String> params) {
        actions.actionSellMarket(params);
    }

    @Override
    public void actionReverse(Map<String, String> params) {

    }

    @Override
    public void actionClosePosition(Map<String, String> params) {
        actions.actionClosePosition(params);
    }

    @Override
    public RxParemetersMap getParametersMap(RxInstrument instrument) {
        return parametersDesk.getTradeParameters(instrument)
                             .getParameterMap();
    }

//    @Override
//    public void actionBreakeven(Map<String, String> params) {
//        actions.updateStopLossToBreakevent(params);
//    }
}
