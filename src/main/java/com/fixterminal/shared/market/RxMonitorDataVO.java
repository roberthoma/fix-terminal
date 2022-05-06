package com.fixterminal.shared.market;

import com.fixterminal.shared.pending_orders.RxPendingOrder;
import com.fixterminal.shared.positions.RxPosition;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class RxMonitorDataVO {


    public RxPosition position ;

    public RxMarketDataCalcBaseVO marketDataCalcBaseVO = new RxMarketDataCalcBaseVO();

    public Map<String, RxPendingOrder> pendingOrdersMap = new HashMap<>();

    public RxMarketDataCalcStrategyVO mdStrategyVo = new RxMarketDataCalcStrategyVO();

    public RxMonitorDataVO(){


    }
}
