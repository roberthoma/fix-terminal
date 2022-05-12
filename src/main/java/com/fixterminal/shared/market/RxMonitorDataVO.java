package com.fixterminal.shared.market;

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


    public RxPosition position ;

    public RxMarketDataCalcBaseVO marketDataCalcBaseVO = new RxMarketDataCalcBaseVO();

    public Map<String, RxPendingOrder> pendingOrdersMap = new HashMap<>();

    public RxMarketDataCalcStrategyVO mdStrategyVo = new RxMarketDataCalcStrategyVO();

    public RxMonitorDataVO(){


    }


    public RxPendingOrder getStopLossOrder(){
        if (pendingOrdersMap.size() > 0){
            for (RxPendingOrder ord : pendingOrdersMap.values()){
                if (ord.getType().equals(RxOrderType.STOP)){
                    return ord;
                }

            }
        }

        return null;
    }

    public RxPendingOrder getTakeProfitOrder(){
        if (pendingOrdersMap.size() > 0){
            for (RxPendingOrder ord : pendingOrdersMap.values()){
                if (ord.getType().equals(RxOrderType.LIMIT)){
                    return ord;
                }

            }
        }

        return null;
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
