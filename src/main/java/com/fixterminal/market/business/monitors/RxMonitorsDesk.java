package com.fixterminal.market.business.monitors;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.market.RxExecuteReport;
import com.fixterminal.shared.market.RxMarketDataVO;
import com.fixterminal.shared.positions.RxPosition;
import com.fixterminal.market.ports.RxQuickFixMessageDispatcherPort;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


//TODO !!!>>> Jak narazie OBSŁUGA TYLKO JEDNEGO INSTRUMENTY

@Component
@Slf4j
public final class RxMonitorsDesk
{

    RxQuickFixMessageDispatcherPort msgDispatcher;


    @Getter
    private final Map<RxInstrument, RxMarketMonitor>  monitorsMap;

    @Autowired
    public RxMonitorsDesk(RxQuickFixMessageDispatcherPort msgDispatcher)  {  // TODO pytanie czy przenieść to do FIXTerminala adaptera
        log.info("Init : RxMonitorsDesk ");
       this.msgDispatcher = msgDispatcher;

       if (msgDispatcher == null){
           log.info("EXCEPTION >>>  RxMonitorsDesk :( msgDispatcher IS NULL");
       }

       monitorsMap = new HashMap<>();

       msgDispatcher.setMarketDataConsumer(this::marketDataDispatcher);


        msgDispatcher.setPositionReportConsumer(position
                -> positionDispatcher(position)
        );


        msgDispatcher.setExecutionReportConsumer(rxExecuteReport ->
                executionReportDispatcher(rxExecuteReport));


    }


    public void initMonitor(RxInstrument instrument){
        if (!monitorsMap.containsKey(instrument)){
            RxMarketMonitor monitor = new RxMarketMonitor(instrument);
            monitor.start();
            monitorsMap.put(monitor.getInstrument() ,monitor);
            System.out.println("> MONITOR  "+instrument.getSymbol()+" is activated");
        }
    }

    public RxMarketMonitor getMonitor(RxInstrument instrument) {
        initMonitor(instrument);
        return  monitorsMap.get(instrument);
    }

    private void marketDataDispatcher(Boolean isFullRefresh,
                                      List<RxMarketDataVO> marketDataVOList  )
    {

        if (marketDataVOList.size() == 0){
            System.out.println("WARNING : marketDataVOList is empty");
            return;
        }

        RxInstrument instrument = marketDataVOList.get(0).instrument;

        if(monitorsMap.containsKey(instrument)) {
            monitorsMap.get(instrument)
                    .marketDataConsume(isFullRefresh, marketDataVOList);
        }
        else {
            System.out.println("ERROR> No set MONITOR for "
                    + instrument.getSymbol()+
                    "  [FIX symbol] = "+instrument.getFixSymbol()
            );
        }

    }

    private void positionDispatcher(RxPosition position) {

        if (position== null) {
            return;
        }
        monitorsMap.get(position.getInstrument())
                .positionReportConsume (position);
    }

    //TODO Utrzożyć dedytkowaną klase dispatchera i wyeliminować encje RxExecuteReport
    private void executionReportDispatcher(RxExecuteReport rxExecuteReport) {

        monitorsMap.get(rxExecuteReport
                .getInstrument()).clearPosition();

//            monitorsMap.get(rxExecuteReport
//                        .getInstrument()).clearPendingOrdersMap();

        monitorsMap.get(rxExecuteReport
                .getInstrument()).executeReportConsume(rxExecuteReport);


    }


}


