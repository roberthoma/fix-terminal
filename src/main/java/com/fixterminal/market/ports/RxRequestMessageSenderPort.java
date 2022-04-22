package com.fixterminal.market.ports;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;

public interface RxRequestMessageSenderPort {
    void  sendMarketDataRequest(RxInstrument instrument);
}
