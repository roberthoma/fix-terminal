package com.fixterminal.market.business.indicators;

import com.fixterminal.market.business.indicators.pivots.RxIndicatorPivots;
import com.fixterminal.shared.market.RxMarketDataCalcBaseVO;
import com.fixterminal.shared.market.RxMarketDataCalcStrategyVO;
import com.fixterminal.shared.market.RxMarketDataVO;
import lombok.Getter;

import java.util.Map;

public class RxIndicators {

    @Getter
    private RxIndicatorPivots indPivots;


    public RxIndicators(){
        indPivots = new RxIndicatorPivots();
    }








    public  void calculate(RxMarketDataCalcStrategyVO mdStrategyVo,
                           RxMarketDataCalcBaseVO mdBaseVO,
                           Map<String, RxMarketDataVO> mdOfferMap
                                          ) {

        indPivots.calculate(mdBaseVO);




    }
}
