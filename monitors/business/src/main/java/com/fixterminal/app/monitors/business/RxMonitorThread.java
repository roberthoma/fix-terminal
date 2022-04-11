package com.fixterminal.app.monitors.business;

import com.fixterminal.shared.base.RxExecuteReportService;
import com.fixterminal.shared.calculators.RxCalculatorMarketDataBase;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.enumerators.RxExecType;
import com.fixterminal.shared.enumerators.RxMDUpdateActionEnum;
import com.fixterminal.shared.enumerators.RxOrderType;
import com.fixterminal.shared.market.RxExecuteReport;
import com.fixterminal.shared.market.RxMarketDataCalcBaseVO;
import com.fixterminal.shared.market.RxMarketDataCalcStrategyVO;
import com.fixterminal.shared.market.RxMarketDataVO;
import com.fixterminal.shared.orders.RxOrdersMap;
import com.fixterminal.shared.pending_orders.RxPendingOrder;
import com.fixterminal.shared.calculators.RxCalculatorPosition;
import com.fixterminal.shared.positions.RxPosition;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;



public class RxMonitorThread extends Thread  {
    private static final Logger log = LoggerFactory.getLogger(RxMonitorThread.class);

    //@Autowired
    //RxCalculatorMarketDataBase rxCalculatorMarketDataBase;

    //RxCalculatorPosition RxCalculatorPosition;


//TODO KONTROLER WYSYŁANYCH ZLECEŃ oczekiwanie na raport


//TODO Prezentacja statystyk np odliczanie czasu od ceny która się zatrzymała na jakiś ustalony czas
// i w ustalonych widełkach cenowych
// kontrola czy rynet jest dostępny  efekt wyłączzonego rynku
//    Position:
//            0) 251687375 1000.0 SHORT 1.13249 11324.9 1132.49
//TODO Informacja o możliwum właczenieu breakeven

    @Setter
    Consumer<RxMarketDataCalcBaseVO> marketDataCalcVoConsumer;

    @Setter
    Consumer<RxPosition> positionConsumer;

    @Setter
    Consumer<RxMarketDataCalcStrategyVO> mdStrategyCalcVoConsumer;

//    @Setter
//    Consumer<RxIndicators>  indicatorConsumer;  //TODO All indicators, but now pivots only

    @Setter
    Consumer<Map<String, RxPendingOrder>> pendingOrdersMapConsumer;

    @Setter
    Consumer<RxMonitorThread> tradeControllerConsumer;
    //TODO to jest bez sensu do zmiany lub poprawy

//    @Getter
//    RxIndicators indicators = new RxIndicators();
//
//    @Getter
//    RxMarketModel   marketModel = new RxMarketModel();

    @Getter
    RxInstrument instrument;

//    @Getter
//    private RxPositionMap positions = new RxPositionMap();

    @Getter
    private RxPosition position ;

    public boolean isOpenPosition(){
      if (position != null) {
      return true;
      }
      return false;
    }

    private RxOrdersMap ordersMap = new RxOrdersMap();

    //private RxPendingOrdersMap pendingOrdersMap = new RxPendingOrdersMap();
    @Getter
    private Map<String, RxPendingOrder>  pendingOrdersMap = new HashMap<>();



    @Getter
    private RxMarketDataCalcBaseVO marketDataCalcBaseVO = new RxMarketDataCalcBaseVO();

    private RxMarketDataCalcStrategyVO mdStrategyVo = new RxMarketDataCalcStrategyVO();


    @Getter
    private final Map mdActualOfferBookMap  = new HashMap();



    public RxMonitorThread(RxInstrument instrument){
        log.info("Init : Monitor of  : "+instrument.getName());
        this.instrument  = instrument;

    }

    public void run(){
        System.out.println("> Start MONITOR "+instrument.getName());
    //x    RxFixTerminal.getInstance().sendMarketDataRequest(instrument);

    //Kalkulacja czasu otwartej pozycji jeżeli tego nie zrobił żaden nachodzący komunikat :)

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
    //TODO send info to client
        //positions.clear();
                this.position = null;
    }

    public void clearPendingOrdersMap(){
        //TODO send info to client
        pendingOrdersMap.clear();
    }



    private void consumers_accept(){
        if (tradeControllerConsumer != null){
            tradeControllerConsumer.accept(this);
        }


        if(marketDataCalcVoConsumer != null) {
            marketDataCalcVoConsumer.accept(marketDataCalcBaseVO);
        }

        if(positionConsumer != null){
            positionConsumer.accept(position);
        }

        if(mdStrategyCalcVoConsumer != null) {
            mdStrategyCalcVoConsumer.accept(mdStrategyVo);
        }

        if (pendingOrdersMapConsumer != null) {
            pendingOrdersMapConsumer.accept(pendingOrdersMap);
        }

//        if (indicatorConsumer != null) {
//            indicatorConsumer.accept(indicators);
//        }


    }

    public void positionReportConsume(RxPosition rxPosition) {

        if (rxPosition != null) {
            this.position = rxPosition;
        }

        consumers_accept();
    }

    public void marketDataConsume(Boolean isFullRefresh,
                                  List<RxMarketDataVO> marketDataVOList  )
    {
        if(isFullRefresh){
            mdActualOfferBookMap.clear();
        }

        marketDataVOList.forEach(rxMarketDataVO
                                 -> refreshOfferBookMap(rxMarketDataVO));

        marketDataCalcBaseVO.reset();
        RxCalculatorMarketDataBase.calculatePrices(marketDataCalcBaseVO,mdActualOfferBookMap);

        if(position != null) {
            RxCalculatorPosition.positionBalanceCalc(position, marketDataCalcBaseVO);
        }


//        indicators.calculate(mdStrategyVo,
//                          marketDataCalcBaseVO,
//                          mdActualOfferBookMap);
//
//        marketModel.determin(indicators,mdActualOfferBookMap, marketDataCalcBaseVO);

        consumers_accept();

    }



    public void executeReportConsume(RxExecuteReport rxExecuteReport) {

        if (rxExecuteReport.getOrderType().equals(RxOrderType.MARKET)){
           return;
        }

        if (  RxExecType.ORDER_STATUS.compareTo(rxExecuteReport.getExecType()) == 0
           || RxExecType.NEW.compareTo(rxExecuteReport.getExecType()) == 0
           || RxExecType.REPLACED.compareTo(rxExecuteReport.getExecType()) == 0
        ) {
            RxPendingOrder rxPendingOrder = RxExecuteReportService.castToPendingOrder(rxExecuteReport);
            pendingOrdersMap.put(rxPendingOrder.getId(),rxPendingOrder);
        }
        else if(RxExecType.CANCEL.compareTo(rxExecuteReport.getExecType()) == 0
              ||RxExecType.TRADE.compareTo(rxExecuteReport.getExecType()) == 0
        )
        {
            pendingOrdersMap.remove(rxExecuteReport.getOrderId());
        }
        else {
            System.out.println(">> NOT IMPLEMENTED ExecType = >"+rxExecuteReport.getExecType()+"<");
        }

        consumers_accept();
    }

   //TODO >>>>>>>>  ustalenie nr zleceń zaczynaących się od np SL-123nr
    //  lub TP-1234nr
    public RxPendingOrder getStopLossOrder(){
        if (pendingOrdersMap.size() > 0){
           for (RxPendingOrder ord : pendingOrdersMap.values()){
             if (ord.getType().equals(RxOrderType.STOP)){
                return ord;
             }

           }
        }

      return null;
    }

    public RxPendingOrder getTakeProfitOrder(){
        if (pendingOrdersMap.size() > 0){
            for (RxPendingOrder ord : pendingOrdersMap.values()){
                if (ord.getType().equals(RxOrderType.LIMIT)){
                    return ord;
                }

            }
        }

        return null;
    }



}
