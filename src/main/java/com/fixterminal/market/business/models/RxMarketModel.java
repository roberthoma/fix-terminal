package com.fixterminal.market.business.models;

import com.fixterminal.market.business.indicators.RxIndicators;
import com.fixterminal.shared.enumerators.RxMarketState;
import com.fixterminal.shared.enumerators.RxMarketTrendCondition;
import com.fixterminal.shared.market.RxMarketDataCalcBaseVO;
import com.fixterminal.shared.market.RxMarketDataVO;
import lombok.Getter;

import java.util.Map;

//TODO create dedicated model for instrument
// TODO Model może modyfikować parametry tradingu lub mieć wpływ na ich modyfikację

public class RxMarketModel {

    @Getter
    RxMarketTrendCondition trendCondition;

    @Getter
    RxMarketState marketState;


    public RxMarketModel(){

        trendCondition = RxMarketTrendCondition.UNDETERMINED;
        marketState = RxMarketState.UNDETERMINED;
    }


        public void determinate(RxIndicators indicators,
                                Map<String, RxMarketDataVO> mdActualOfferBookMap,
                                RxMarketDataCalcBaseVO marketDataCalcBaseVO)
    {
        int  pivotsSize = indicators.getIndPivots().getSize();
        System.out.println("Market State : "+ trendCondition+ " > "  +marketState );

      //  mdActualOfferBookMap.forEach((s, rxMarketDataVO) -> System.out.println(" DATA>" + rxMarketDataVO));


    }
}
