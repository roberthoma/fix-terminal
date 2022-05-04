package com.fixterminal.market.adapters;

import com.fixterminal.app.ports.RxMarketTraderPort;
import com.fixterminal.market.business.trader.actions.RxTradeActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RxMarketTraderAdapter implements RxMarketTraderPort {

    RxTradeActions actions;

    @Autowired
    RxMarketTraderAdapter(RxTradeActions actions){
        this.actions = actions;
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
}
