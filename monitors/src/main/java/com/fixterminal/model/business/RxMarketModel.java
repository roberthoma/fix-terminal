package com.fixterminal.model.business;

import com.fixterminal.model.enumerators.RxMarketState;
import com.fixterminal.model.enumerators.RxMarketTrendCondition;
import lombok.Getter;

import java.util.Map;

public class RxMarketModel {

    @Getter
    RxMarketTrendCondition trendCondition;

    @Getter
    RxMarketState marketState;


    public RxMarketModel(){
        trendCondition = RxMarketTrendCondition.UNDETERMINED;
        marketState = RxMarketState.UNDETERMINED;
    }


//        public void determin(RxIndicators indicators,
//    Map mdActualOfferBookMap,
//    RxMarketDataCalcBaseVO marketDataCalcBaseVO)
//    {
//
//        if (indicators.getIndPivots().getPivots().size() > 3) {
//            trendCondition = RxMarketTrendCondition.UPTREND;
//            marketState = RxMarketState.OVERSOLD;
//        }
//
//
//    }
}
