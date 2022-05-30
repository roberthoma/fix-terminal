package com.fixterminal.market.business.monitors;

import com.fixterminal.market.business.trade.actions.RxActionsManager;
import com.fixterminal.market.ports.RxMessageSenderPort;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.enumerators.RxRequestStatus;
import com.fixterminal.shared.market.RxExecuteReport;
import com.fixterminal.shared.market.RxMarketDataVO;
import com.fixterminal.shared.positions.RxPosition;
import com.fixterminal.market.ports.RxQuickFixMessageDispatcherPort;
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
    RxActionsManager actionsController;

    private final Map<RxInstrument, RxMonitor>  monitorsMap;


    @Autowired
    public RxMonitorsDesk(RxQuickFixMessageDispatcherPort msgDispatcher,
                          RxMessageSenderPort messageSender,
                          RxActionsManager actionsController
                          )
    {

        log.info("Init : RxMonitorsDesk ");
       this.msgDispatcher = msgDispatcher;
       this.messageSender = messageSender;
       this.actionsController = actionsController;

       if (msgDispatcher == null){
           log.info("EXCEPTION >>>  RxMonitorsDesk :( msgDispatcher IS NULL");
       }

       monitorsMap = new HashMap<>();

        assert msgDispatcher != null;
        msgDispatcher.setMarketDataConsumer(this::marketDataDispatcher);


        msgDispatcher.setPositionReportConsumer(this::positionReportDispatcher);


    //    msgDispatcher.setExecutionReportConsumer(this::executionReportDispatcher);


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
//            System.out.println("ROHO marketDataConsume isFullRefresh="+isFullRefresh+ " marketDataVOList="+marketDataVOList);
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

    private void positionReportDispatcher(RxPosition position) {
        System.out.println("MONITOR_DESK>POSITION_DISPATCHER>"+position);
        if (position == null) {
            monitorsMap.forEach((instrument, rxMonitor) -> rxMonitor.clearPosition());
        //    actionsController.setPositionReportStatus(RxRequestStatus.RECEIVED);
            return;
        }
        monitorsMap.get(position.getInstrument()).positionReportConsume(position);

     //   actionsController.setPositionReportStatus(position.getInstrument(), RxRequestStatus.RECEIVED);
    }

    //TODO Utrzożyć dedytkowaną klase dispatchera i wyeliminować encje RxExecuteReport
//    private void executionReportDispatcher(RxExecuteReport rxExecuteReport) {
//
////        monitorsMap.get(rxExecuteReport
////                .getInstrument()).clearPosition();
//
//        messageSender.sendRequestForPositions(); //TODO napewnoe nie tutaj
//
// //       actionsController.setPositionReportStatus(rxExecuteReport.getInstrument(),RxRequestStatus.SENT);
//
//
//
////            monitorsMap.get(rxExecuteReport
////                        .getInstrument()).clearPendingOrdersMap();
//        actionsController.executeReportConsume(rxExecuteReport);
//       // monitorsMap.get(rxExecuteReport.getInstrument()).executeReportConsume(rxExecuteReport);
//
//
//
//
//    }


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


