package com.fixterminal.shared.calculators;


import com.fixterminal.shared.enumerators.RxPositionDirection;
import com.fixterminal.shared.market.RxMarketDataCalcBaseVO;
import com.fixterminal.shared.positions.RxPosition;
import org.springframework.stereotype.Service;

@Service
public class RxCalculatorPosition {

    private static void calcPosition(RxPosition position, RxMarketDataCalcBaseVO mdCalcVo ){

        Double diffprice;

        if ( position.getDirection().compareTo(RxPositionDirection.SHORT)==0) {
            diffprice = position.getEntryPrice() - mdCalcVo.offerBestPrice;
        }
        else{
            diffprice =   mdCalcVo.bidBestPrice - position.getEntryPrice();
        }

//        int pipPos = RxDictionaries.getInstance()
//                      .getInstruments()
//                      .getByFixSymbol(position.getInstrument().getFixSymbol())
//                      .getPipPosition();
        int pipPos = 4; //RxDictionaries.getInstance()  //TODO Tymczasowe


        position.setPips(diffprice * Math.pow(10, pipPos ));

        position.setGross(diffprice*position.getQuantity());


        //TODO Net calculation
    }


//    public static void positionBalanceCalc(Map<String, RxPosition> positionMap ,
//                                           RxMarketDataCalcBaseVO mdCalcVo)
//    {
//        positionMap.forEach((s, rxPosition) ->  calcPosition(rxPosition,mdCalcVo ));
//    }

    public static void positionBalanceCalc( RxPosition position ,
                                           RxMarketDataCalcBaseVO mdCalcVo)
    {
          calcPosition(position,mdCalcVo );
    }

}
