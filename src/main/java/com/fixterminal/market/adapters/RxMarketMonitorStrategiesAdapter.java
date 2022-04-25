package com.fixterminal.market.adapters;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.market.RxMarketDataCalcBaseVO;
import com.fixterminal.shared.pending_orders.RxPendingOrder;
import com.fixterminal.shared.positions.RxPosition;
import com.fixterminal.strategies.ports.RxMarketMonitorStrategiesPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
public class RxMarketMonitorStrategiesAdapter implements RxMarketMonitorStrategiesPort {

    public RxMarketMonitorStrategiesAdapter(RxInstrument instrument){

    }

    @Override
    public void setTradeControllerConsumer(Consumer<RxMarketMonitorStrategiesPort> m) {

    }

    @Override
    public boolean isOpenPosition() {
        return false;
    }

    @Override
    public RxPosition getPosition() {
        return null;
    }

    @Override
    public RxInstrument getInstrument() {
        return null;
    }

    @Override
    public RxMarketDataCalcBaseVO getMarketDataCalcBaseVO() {
        return null;
    }

    @Override
    public RxPendingOrder getStopLossOrder() {
        return null;
    }
}
