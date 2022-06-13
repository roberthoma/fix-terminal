package com.fixterminal.market.business.indicators.pivots;

import com.fixterminal.shared.enumerators.RxMarketTrendCondition;
import com.fixterminal.shared.enumerators.RxPivotType;
import com.fixterminal.shared.indicators.RxPivot;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RxIndicatorPivotsStatistic {

//TODO informacje o coraz to mniejszych dystansach między piwotami . np konsolidacja
// trend wzrastający - coraz to wyższypiwot itp

       @Getter
       Double maxPrice;

       @Getter
       Double minPrice;

       @Getter
       Double avgDistance;

       @Getter
       RxMarketTrendCondition trendCondition = RxMarketTrendCondition.UNDETERMINED;

       List<Double> distanceList = new ArrayList<>();

       @Getter
       Double maxMinDistance;

       int distCounter = 0;
       Double distance;

       private void setMaxMin(RxPivot pivot){
           if (pivot.type == RxPivotType.DOWN){
               if(   minPrice == null
                  || minPrice > pivot.price)
               {
                   minPrice = pivot.price;
               }

           }

           if (pivot.type == RxPivotType.UP){
               if(   maxPrice == null
                  || maxPrice < pivot.price)
               {
                   maxPrice = pivot.price;
               }

           }

       }

    private void setMaxMinDistance(){

        if(maxPrice != null && minPrice != null) {
            maxMinDistance = maxPrice - minPrice;
        }
    }

        private void setAvgDistance(RxPivot prevPivot,  RxPivot pivot){
            if (prevPivot.type == RxPivotType.START){
               return;
            }
            if (prevPivot.id != pivot.id) {
                distanceList.add(Math.abs(prevPivot.price - pivot.price));
            }

            if (distanceList.size() > 2){
                distance = 0.0;
                distCounter = 0;
                distanceList.forEach(pdist -> { distCounter++; distance += pdist;});
                avgDistance = distance / distCounter;
            }


        }

       public void calculate(List<RxPivot> pivots) {
           RxPivot prevPivot = new RxPivot();
           prevPivot.type = RxPivotType.START;

           distanceList.clear();


          for (RxPivot pivot : pivots  ){
              setMaxMin(pivot);
              setMaxMinDistance();
              setAvgDistance(prevPivot, pivot);
              prevPivot = pivot;
          }

           determinateTrendCondition(pivots);

       }


       private void determinateTrendCondition(List<RxPivot> pivots){

           int  pSize = pivots.size();

           if (pSize < 6){
              return;
           }

//           List<RxPivot> upPivotsList   = pivots.stream()
//                                                .filter(rxPivot -> rxPivot.type.equals(RxPivotType.UP))
//                                                .collect(Collectors.toList());
//
//           List<RxPivot> downPivotsList   = pivots.stream()
//                   .filter(rxPivot -> rxPivot.type.equals(RxPivotType.DOWN))
//                   .collect(Collectors.toList());



           if (   pivots.get(pSize -2).price > pivots.get(pSize -4).price
               && pivots.get(pSize -3).price > pivots.get(pSize -5).price
               )
           {
               trendCondition = RxMarketTrendCondition.UPTREND;
               return;
           }

           if (   pivots.get(pSize -2).price < pivots.get(pSize -4).price
                   && pivots.get(pSize -3).price < pivots.get(pSize -5).price
           )
           {
               trendCondition = RxMarketTrendCondition.DOWNTREND;
               return;
           }

           trendCondition = RxMarketTrendCondition.UNDETERMINED;
       }


}
