package com.fixterminal.strategies.ports;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.market.RxMarketDataCalcBaseVO;
import com.fixterminal.shared.pending_orders.RxPendingOrder;
import com.fixterminal.shared.positions.RxPosition;

import java.util.function.Consumer;

public interface RxMarketMonitorStrategiesPort {

    //public RxMarketMonitorTraderPort(RxInstrument instrument);

    void setTradeControllerConsumer(Consumer<RxMarketMonitorStrategiesPort> m);

    boolean isOpenPosition();

    RxPosition getPosition();

    RxInstrument getInstrument();

    RxMarketDataCalcBaseVO getMarketDataCalcBaseVO();

    RxPendingOrder getStopLossOrder();
}
