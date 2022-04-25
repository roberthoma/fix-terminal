package com.fixterminal.market.ports;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;

public interface RxMessageSenderPort {
    void  sendMarketDataRequest(RxInstrument instrument);
}
