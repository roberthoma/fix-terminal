package com.fixterminal.monitors;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.market.RxExecuteReport;
import com.fixterminal.shared.market.RxMarketDataVO;
import com.fixterminal.shared.positions.RxPosition;
import com.fixterminal.terminal.adapters.RxQuickFixMessageDispatcherPort;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


//TODO !!!>>> Jak narazie OBSŁUGA TYLKO JEDNEGO INSTRUMENTY

@Component
public class RxMonitorsDesk
{
    private static final Logger log = LoggerFactory.getLogger(RxMonitorsDesk.class);

    RxQuickFixMessageDispatcherPort msgDispatcher;


    @Getter
    private Map<RxInstrument, RxMonitorThread>  monitorsMap;

    @Autowired
    public RxMonitorsDesk(RxQuickFixMessageDispatcherPort msgDispatcher)  {  // TODO pytanie czy przenieść to do FIXTerminala adaptera
       this.msgDispatcher = msgDispatcher;


if (msgDispatcher !=null) {
    log.info("Init : RxMonitorsDesk " + msgDispatcher.toString());
}
else {
    log.info("Init : RxMonitorsDesk :( msgDispatcher IS NULL");
}

        monitorsMap = new HashMap<>();

       msgDispatcher.setMarketDataConsumer(this::marketDataDispatcher);


        msgDispatcher.setPositionReportConsumer(position
                -> positionDispatcher(position)
        );


        msgDispatcher.setExecutionReportConsumer(rxExecuteReport ->
                executionReportDispatcher(rxExecuteReport));


    }


    private void initMonitor(RxInstrument instrument){
        if (!monitorsMap.containsKey(instrument)){
            RxMonitorThread monitor = new RxMonitorThread(instrument);
            monitor.start();
            monitorsMap.put(monitor.getInstrument() ,monitor);
            System.out.println("> MONITOR  "+instrument.getSymbol()+" is activated");
        }
    }

    public RxMonitorThread getMonitor(RxInstrument instrument) {
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


