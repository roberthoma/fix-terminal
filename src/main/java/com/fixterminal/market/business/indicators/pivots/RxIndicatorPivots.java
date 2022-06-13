package com.fixterminal.market.business.indicators.pivots;

import com.fixterminal.shared.enumerators.RxPivotType;
import com.fixterminal.shared.indicators.RxPivot;
import com.fixterminal.shared.market.RxMarketDataCalcBaseVO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

//TODO kontrola ilośći pivotów up down .. powinno być +- 1 tyle samo
@Slf4j
public class RxIndicatorPivots {

    @Getter
    private RxIndicatorPivotsStatistic pivotsStatistic  = new RxIndicatorPivotsStatistic();

    private final List<RxPivot> pivots = new ArrayList<>();

    private double minPriceDiff =  0.0001; //TODO parametryzacja w pliku

    private void addPivot(RxPivot pivot){
        pivot.id = pivots.size() + 1;
        pivots.add(pivot);
        System.out.println("Adding new "+ pivot.type +" pivot  : "+pivot.price+"  "+ pivot.dateTime+ "  "+
                " all is = "+pivots.size()
        );

    }

    private void replaceLastPivot(RxMarketDataCalcBaseVO mdBaseVO){

        int lastIdx = pivots.size() -1;

        pivots.get(lastIdx).dateTime = mdBaseVO.dateTime;

        if (pivots.get(lastIdx).type == RxPivotType.UP) {
            pivots.get(lastIdx).price = mdBaseVO.offerBestPrice;
        }
        else {
            pivots.get(lastIdx).price = mdBaseVO.bidBestPrice;
        }
       System.out.println("Replacing "+pivots.get(lastIdx).type + " pivot : price="+pivots.get(lastIdx).price);


    }

    private void pivotsCalculate(RxMarketDataCalcBaseVO mdBaseVO){
        int upPn = pivots.size();

    //    System.out.println("MD_BASE > "+ mdBaseVO);
     //   System.out.println("PIVOTS size="+ pivots.size());


        if (upPn == 0){
            addPivot( RxPivotsFactory.mdBaseVoToPivot(RxPivotType.START,mdBaseVO ) );
            return;
        }

        //Duration minDur = Duration.ofSeconds(30);

        RxPivot lastPivot = pivots.get(upPn-1);


        //Duration lastPivotDuration = Duration.between(lastPivot.dateTime, mdBaseVO.dateTime);

        double diffLastPivotPrice;
        if (lastPivot.type == RxPivotType.DOWN) {
            diffLastPivotPrice = Math.abs(lastPivot.price - mdBaseVO.bidBestPrice);
        }
        else {
            diffLastPivotPrice = Math.abs(lastPivot.price - mdBaseVO.offerBestPrice);
        }

        if (diffLastPivotPrice > minPriceDiff)
        {
            if (   mdBaseVO.offerBestPrice > lastPivot.price
                    && ( lastPivot.type == RxPivotType.DOWN || lastPivot.type == RxPivotType.START)
            )
            {
                addPivot( RxPivotsFactory.mdBaseVoToPivot(RxPivotType.UP,mdBaseVO ) );
                return;
            }

            if (mdBaseVO.bidBestPrice < lastPivot.price
                    && (lastPivot.type == RxPivotType.UP || lastPivot.type == RxPivotType.START)
            )
            {
                addPivot( RxPivotsFactory.mdBaseVoToPivot(RxPivotType.DOWN,mdBaseVO ) );
                return;
            }
        }

        if(lastPivot.type == RxPivotType.UP){
            if (mdBaseVO.offerBestPrice > lastPivot.price ){
                replaceLastPivot(mdBaseVO);
                return;
            }
        }

        if(lastPivot.type == RxPivotType.DOWN){
            if (mdBaseVO.bidBestPrice < lastPivot.price ){
                replaceLastPivot(mdBaseVO);
                return;
            }
        }
    }

    public void calculate(RxMarketDataCalcBaseVO mdBaseVO) {
        pivotsCalculate(mdBaseVO);
        pivotsStatistic.calculate(pivots); //TODO wykonać tylko gdy jest ustalony już  pivot


        System.out.println("PIVOTS TREND : "+ pivotsStatistic.getTrendCondition());
    }


    public int getSize() {
    return  pivots.size();
    }
}
