package com.fixterminal.market.business.models;

import com.fixterminal.shared.enumerators.RxMarketState;
import com.fixterminal.shared.enumerators.RxMarketTrendCondition;
import lombok.Getter;

//TODO create dedicated model for instrument
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
