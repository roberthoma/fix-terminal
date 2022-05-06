package com.fixterminal.market.ports;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.orders.RxOrderEntity;
import com.fixterminal.shared.pending_orders.RxPendingOrder;

public interface RxMessageSenderPort {
    void  sendMarketDataRequest(RxInstrument instrument);

    String sendNewOrderSingle(RxOrderEntity order);

    void sendRequestForPositions();

    void sendOrderReplaceRequest(RxPendingOrder orderSL);
}
