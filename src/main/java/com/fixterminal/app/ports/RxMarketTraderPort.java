package com.fixterminal.app.ports;

public interface RxMarketTraderPort {

    void actionBuyMarket();
    void actionSellMarket();
    void actionReverse();
    void actionClosePosition();

}
