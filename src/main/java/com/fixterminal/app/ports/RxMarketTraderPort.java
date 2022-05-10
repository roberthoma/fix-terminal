package com.fixterminal.app.ports;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.parameters.RxParemetersMap;


public interface RxMarketTraderPort {

    void actionBuyMarket();
    void actionSellMarket();
    void actionReverse();
    void actionClosePosition();

    RxParemetersMap getParametersMap(RxInstrument instrument);
}
