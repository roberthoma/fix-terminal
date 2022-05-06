package com.fixterminal.market.business.monitors;

import com.fixterminal.market.ports.RxMessageSenderPort;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.market.RxExecuteReport;
import com.fixterminal.shared.market.RxMarketDataVO;
import com.fixterminal.shared.positions.RxPosition;
import com.fixterminal.market.ports.RxQuickFixMessageDispatcherPort;
import com.fixterminal.terminal.business.senders.RxRequestMessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//TODO !!!>>> Jak narazie OBSŁUGA TYLKO JEDNEGO INSTRUMENTY

//TODO inicjować monitory przed  logowaniem do rynku

@Component
@Slf4j
public final class RxMonitorsDesk
{

    RxQuickFixMessageDispatcherPort msgDispatcher;
    RxMessageSenderPort messageSender;


    //@Getter
    private final Map<RxInstrument, RxMonitor>  monitorsMap;


    @Autowired
    public RxMonitorsDesk(RxQuickFixMessageDispatcherPort msgDispatcher,
                          RxMessageSenderPort messageSender
                          )
    {

        log.info("Init : RxMonitorsDesk ");
       this.msgDispatcher = msgDispatcher;
       this.messageSender = messageSender;

       if (msgDispatcher == null){
           log.info("EXCEPTION >>>  RxMonitorsDesk :( msgDispatcher IS NULL");
       }

       monitorsMap = new HashMap<>();

        assert msgDispatcher != null;
        msgDispatcher.setMarketDataConsumer(this::marketDataDispatcher);


        msgDispatcher.setPositionReportConsumer(this::positionDispatcher);


        msgDispatcher.setExecutionReportConsumer(this::executionReportDispatcher);


    }


    public void initMonitor(RxInstrument instrument){
        if (!monitorsMap.containsKey(instrument)){
            RxMonitor monitor = new RxMonitor(instrument);
            monitor.start();
            monitorsMap.put(monitor.getInstrument() ,monitor);
            System.out.println("> MONITOR  "+instrument.getSymbol()+" is activated");
        }
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
        System.out.println("ROHO positionDispatcher >"+position);
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

        messageSender.sendRequestForPositions();
    }


    public RxMonitor getMonitor(RxInstrument intrument){
       //TODO obsługa błędów.
       return  monitorsMap.get(intrument);

    }


    public List<RxInstrument> getMonitoredInstrumentList() {
        List<RxInstrument> instruments = new ArrayList<>();
        monitorsMap.forEach((rxInstrument, rxMonitorThread) -> instruments.add(rxInstrument));
        return instruments;
    }
public List<RxMonitor> getMonitorsList(){
    List<RxMonitor> mlist = new ArrayList<>();
    monitorsMap.forEach((instrument, rxMonitor) -> mlist.add(rxMonitor));
    return mlist;
}

}


