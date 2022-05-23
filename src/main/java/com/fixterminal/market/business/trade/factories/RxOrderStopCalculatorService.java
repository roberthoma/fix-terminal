package com.fixterminal.market.business.trade.factories;

import com.fixterminal.shared.enumerators.RxPositionDirection;

public class RxOrderStopCalculatorService {


     public static double breakevenPriceCalc(RxPositionDirection direction,
                                             Double entryPrice,
                                             Double breakevenProfit)
     {
         double brePrice;
         if(direction.equals(RxPositionDirection.LONG)){
             brePrice =  entryPrice + breakevenProfit;
         }else {
             brePrice =  entryPrice - breakevenProfit;
         }
       return brePrice;
     }


//    public static double trailingStopLossPriceCalc(RxPositionDirection direction,
//                              Double marketPrice,
//                              Double trailingFollowDist){
//
//       double slPrice;
//
//
// ????
//
//
//      return slPrice;
//    }


}
