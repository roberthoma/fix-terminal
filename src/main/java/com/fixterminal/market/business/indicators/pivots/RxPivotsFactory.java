package com.fixterminal.market.business.indicators.pivots;


import com.fixterminal.shared.enumerators.RxPivotType;
import com.fixterminal.shared.indicators.RxPivot;
import com.fixterminal.shared.market.RxMarketDataCalcBaseVO;

public class RxPivotsFactory {

    public static RxPivot mdBaseVoToPivot(RxPivotType pivotType, RxMarketDataCalcBaseVO mdBaseVO){
        RxPivot pivot = new RxPivot();

        pivot.type = pivotType;
        pivot.dateTime = mdBaseVO.dateTime;
        if (pivotType == RxPivotType.UP) {
            pivot.price = mdBaseVO.offerBestPrice;
        }
        else {
            pivot.price = mdBaseVO.bidBestPrice;
        }
        return pivot;
    }
}
