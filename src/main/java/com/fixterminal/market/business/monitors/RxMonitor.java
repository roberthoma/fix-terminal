package com.fixterminal.market.business.monitors;

import com.fixterminal.market.business.indicators.RxIndicators;
import com.fixterminal.shared.market.*;
import com.fixterminal.market.business.calculators.RxCalculatorMarketBaseData;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.enumerators.RxMDUpdateActionEnum;
import com.fixterminal.market.business.calculators.RxCalculatorPosition;
import com.fixterminal.shared.positions.RxPosition;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


@Slf4j
public class RxMonitor extends Thread  {

     List<Consumer<RxPosition>> positionConsumerList;  //TODO pozycje poza kontrolą monitora
     //List<Consumer<Map<String, RxPendingOrder>>> pendingOrdersConsumerList;


     List<Consumer<RxMarketDataCalcBaseVO>> marketDataCalcVoConsumerList;

     List<Consumer<RxIndicators>> indicatorsConsumersList;

     List<Consumer<RxMonitorDataVO>> marketDataConsumerList;

     @Getter
     private RxMonitorDataVO data = new RxMonitorDataVO();




    @Getter
    private final Map<String, RxMarketDataVO> mdActualOfferBookMap  = new HashMap<>();



    public RxMonitor(RxInstrument instrument){
        log.info("Init : Monitor of  : "+instrument.getSymbol());

        data.instrument  = instrument;

        positionConsumerList = new ArrayList<>();
        marketDataCalcVoConsumerList = new ArrayList<>();
        marketDataConsumerList = new ArrayList<>();
    }

    public RxInstrument getInstrument(){
        return data.instrument;
    }

    public void run(){

        System.out.println("> Start MONITOR "+data.instrument.getSymbol());

        //TODO Kalkulacja czasu otwartej pozycji jeżeli tego nie zrobił żaden nachodzący komunikat :)

    }


    private void refreshOfferBookMap(RxMarketDataVO rxMarketDataVO){
        if (rxMarketDataVO.action == RxMDUpdateActionEnum.DELETE){
            mdActualOfferBookMap.remove(rxMarketDataVO.entryID);
        }
        else {
            mdActualOfferBookMap.put(rxMarketDataVO.entryID, rxMarketDataVO);
        }
    }
    public void clearPosition(){
        data.position = null;
        System.out.println("MONITOR> " + data.instrument.getSymbol() +" : clear tab position.");
    }

    private void consumers_accept(){

       marketDataCalcVoConsumerList.forEach(c -> c.accept(data.marketDataCalcBaseVO));

       positionConsumerList.forEach(c -> c.accept(data.position));


       marketDataConsumerList.forEach(c -> c.accept(data));

    }


    public void positionReportConsume(RxPosition rxPosition) {

        if (rxPosition != null) {
            data.position = rxPosition;
        }

        positionConsumerList.forEach(c -> c.accept(data.position));
    }

    public void marketDataConsume(Boolean isFullRefresh,
                                  List<RxMarketDataVO> marketDataVOList  )
    {
        if(isFullRefresh){
            mdActualOfferBookMap.clear();
        }

        marketDataVOList.forEach(this::refreshOfferBookMap);

        data.marketDataCalcBaseVO.reset();
        RxCalculatorMarketBaseData.calculatePrices(data.marketDataCalcBaseVO,mdActualOfferBookMap);

        if(data.position != null) {
            RxCalculatorPosition.positionBalanceCalc(data.position, data.marketDataCalcBaseVO);
        }

        data.getIndicators().calculate(data.mdStrategyVo,
                             data.marketDataCalcBaseVO,
                          mdActualOfferBookMap);


        data.marketModel.determinate(data.getIndicators(),mdActualOfferBookMap, data.marketDataCalcBaseVO);

        consumers_accept();

    }




    //TODO Cała ta funkcja do przeanalizowania

    //TODO >>>>>>>>  ustalenie nr zleceń zaczynaących się od np SL-123nr
    //  lub TP-1234nr

    public void  addMarketDataCalcVoConsumer(Consumer<RxMarketDataCalcBaseVO> consumer){
        marketDataCalcVoConsumerList.add(consumer);
    }

    public void addMarketDataConsumer(Consumer<RxMonitorDataVO> consumer) {
        marketDataConsumerList.add(consumer);
    }


//    public RxPosition getPosition() {
//      return data.position;
//    }

    public RxMarketDataCalcBaseVO getMarketDataCalcBaseVO() {
      return data.marketDataCalcBaseVO;
    }
}
