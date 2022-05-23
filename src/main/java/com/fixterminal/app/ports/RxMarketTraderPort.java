package com.fixterminal.app.ports;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.parameters.RxParemetersMap;

import java.util.Map;


public interface RxMarketTraderPort {

    void actionBuyMarket(Map<String, String> params);
    void actionSellMarket(Map<String, String> params);
    void actionReverse(Map<String, String> params);
    void actionClosePosition(Map<String, String> params);

    RxParemetersMap getParametersMap(RxInstrument instrument);

 //   void actionBreakeven(Map<String, String> params);
}
