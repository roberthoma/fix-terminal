package com.fixterminal.market.business.trader.factories;

import com.fixterminal.market.business.parameters.RxTradeParameters;
import com.fixterminal.shared.enumerators.RxPositionDirection;
//import org.springframework.stereotype.Service;

public class RxOrderStopCalculatorService {


     public static double breakEvenPriceCalc(RxPositionDirection direction,
                               Double entryPrice,
                               RxTradeParameters parameters)
     {
         double brePrice;
         if(direction.equals(RxPositionDirection.LONG)){
             brePrice =  entryPrice + parameters.getBreakeventProfit();
         }else {
             brePrice =  entryPrice - parameters.getBreakeventProfit();
         }
       return brePrice;
     }

}
