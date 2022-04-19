package com.fixterminal.terminal.adapters;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;

public interface RxRequestMessageSenderPort {
    void  sendMarketDataRequest(RxInstrument instrument);
}
