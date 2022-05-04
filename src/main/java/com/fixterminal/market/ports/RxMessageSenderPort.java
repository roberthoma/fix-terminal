package com.fixterminal.market.ports;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.orders.RxOrderEntity;

public interface RxMessageSenderPort {
    void  sendMarketDataRequest(RxInstrument instrument);

    String sendNewOrderSingle(RxOrderEntity order);

    void sendRequestForPositions();
}
