package com.fixterminal.market.business.calculators;


import com.fixterminal.shared.enumerators.RxPositionDirection;
import com.fixterminal.shared.market.RxMarketDataCalcBaseVO;
import com.fixterminal.shared.positions.RxPosition;
import org.springframework.stereotype.Service;

@Service
public class RxCalculatorPosition {

    private static void calcPosition(RxPosition position, RxMarketDataCalcBaseVO mdCalcVo ){

        Double marketDistance;


        //System.out.println("ROHO mdCalcVo > "+mdCalcVo.toString());

        if ( position.getDirection().compareTo(RxPositionDirection.SHORT)==0) {
            marketDistance      = position.getEntryPrice() - mdCalcVo.offerBestPrice;
        }
        else{
            marketDistance      = mdCalcVo.bidBestPrice - position.getEntryPrice();
        }

//        int pipPos = RxDictionaries.getInstance()
//                      .getInstruments()
//                      .getByFixSymbol(position.getInstrument().getFixSymbol())
//                      .getPipPosition();
        int pipPos = 4; //RxDictionaries.getInstance()  //TODO Tymczasowe


        position.setMarketDistance(marketDistance);

        position.setPips(marketDistance * Math.pow(10, pipPos ));

        position.setGross(marketDistance*position.getQuantity());


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
