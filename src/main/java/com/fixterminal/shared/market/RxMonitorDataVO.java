package com.fixterminal.shared.market;

import com.fixterminal.market.business.indicators.RxIndicators;
import com.fixterminal.market.business.models.RxMarketModel;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.enumerators.RxOrderType;
import com.fixterminal.shared.pending_orders.RxPendingOrder;
import com.fixterminal.shared.positions.RxPosition;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class RxMonitorDataVO {

    public RxInstrument instrument;

    @Getter
    RxIndicators indicators = new RxIndicators();


    public RxPosition position ;

    public RxMarketDataCalcBaseVO marketDataCalcBaseVO = new RxMarketDataCalcBaseVO();

//    public Map<String, RxPendingOrder> pendingOrdersMap = new HashMap<>();

    public RxMarketDataCalcStrategyVO mdStrategyVo = new RxMarketDataCalcStrategyVO();

    public RxMarketModel marketModel = new RxMarketModel();

    public RxMonitorDataVO(){


    }


    public RxPosition getPosition() {
        return position;
    }


    public boolean isOpenPosition(){
        if (position != null) {
            return true;
        }
        return false;
    }

    public RxInstrument getInstrument() {
       return instrument;
    }
}
