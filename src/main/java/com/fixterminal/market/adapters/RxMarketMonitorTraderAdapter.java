package com.fixterminal.market.adapters;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.market.RxMarketDataCalcBaseVO;
import com.fixterminal.shared.pending_orders.RxPendingOrder;
import com.fixterminal.shared.positions.RxPosition;
import com.fixterminal.strategies.ports.RxMarketMonitorTraderPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class RxMarketMonitorTraderAdapter implements RxMarketMonitorTraderPort {



//    @Autowired
//    public RxMarketMonitorTraderAdapter(Rx){
//      super();
//    }

    @Override
    public void setTradeControllerConsumer(Consumer<RxMarketMonitorTraderPort> m) {

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
