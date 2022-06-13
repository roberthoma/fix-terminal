package com.fixterminal.market.business.trade.actions;

import com.fixterminal.market.business.trade.orders.RxOrdersManager;
import com.fixterminal.market.ports.RxMessageSenderPort;
import com.fixterminal.market.ports.RxQuickFixMessageDispatcherPort;
import com.fixterminal.shared.dictionaries.instruments.RxDicInstruments;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.enumerators.*;
import com.fixterminal.shared.market.RxExecuteReport;
import com.fixterminal.shared.orders.RxOrderEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.tools.ant.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RxActionsManager {

    Map<RxActionKey, RxActionStatus>   actionStatusMap =  new HashMap<>();


    RxDicInstruments dicInstruments;
    RxOrdersManager ordersManager;
    RxQuickFixMessageDispatcherPort msgDispatcher;
    RxMessageSenderPort messageSender;

    RxActionService  actionService;

    @Autowired
    RxActionsManager(RxDicInstruments dicInstruments,
                     RxQuickFixMessageDispatcherPort msgDispatcher,
                     RxMessageSenderPort messageSender,
                     RxOrdersManager ordersManager,
                     RxActionService  actionService
    ){
      log.info("Init : RxTradeActionsController");
      this.dicInstruments = dicInstruments;
      this.msgDispatcher = msgDispatcher;
      this.messageSender = messageSender;
      this.ordersManager = ordersManager;
      this.actionService = actionService;

      ordersManager.setExecuteReportConsumer(this::checkExeReport);
    }



//    public void setPositionReportStatus(RxInstrument instrument, RxRequestStatus status) {
//        //System.out.println(">>> getPositionReportStatus>>>> PUT "+instrument.getSymbol() + " "+status);
//
//        positionReportStatusMap.put(instrument,status);
//    }
//    public void setPositionReportStatus(RxRequestStatus status) {
//        for (RxInstrument instrument : dicInstruments.toList())
//        {
//          //  System.out.println(">>> getPositionReportStatus for ALL>>>> PUT "+instrument.getSymbol() + " "+status);
//            positionReportStatusMap.put(instrument,status);
//        }
//
//
//    }



//    public RxRequestStatus getPositionReportStatus(RxInstrument instrument) {
//        return positionReportStatusMap.get(instrument);
//    }

    public void startAction(RxActionType action, RxOrderEntity order) {

        actionStatusMap.put(new RxActionKey(order.getFixSymbol(),action),RxActionStatus.STARTED);
        printActionStatusMap();
        order.setOrderStatus(RxOrderStatus.SENT);  //TODO Czy w tym miejscu
        ordersManager.put(order);
        ordersManager.ordersMapPrint();
    }



    public void checkExeReport(RxExecuteReport report){

        RxActionType actionType =  actionService.reportToActionType(report);
        System.out.println("ACTIONS_MANAGER>checkExeReport> "+actionType);

        RxActionKey   actionKey  = new RxActionKey (report.getInstrument().getFixSymbol(),actionType);

        if (actionStatusMap.containsKey(actionKey)) {
            if (report.getOrderType().equals(RxOrderType.STOP)
            &&  report.getOrderStatus().equals(RxOrderStatus.NEW)
            ) {
                actionStatusMap.put(actionKey,RxActionStatus.FINISHED);
            }

            if (report.getOrderType().equals(RxOrderType.MARKET)
                    &&  report.getOrderStatus().equals(RxOrderStatus.FILLED)
            ) {
                actionStatusMap.put(actionKey,RxActionStatus.FINISHED);
            }


            //actionStatusMap.get(actionKey);
        }
        else {
            System.out.println(">>>>>>>> Brak takiej akcji !!!!!!!!!!");
        }

    }

    public void check(RxActionType action, RxInstrument instrument) throws Exception{ //TODO zdefiniować wyjątek
        RxActionKey actionKey = new RxActionKey(instrument.getFixSymbol(),action);
        System.out.println("ACTIONS_MANAGER>check : "+actionKey);
        printActionStatusMap();
        if (actionStatusMap.containsKey(actionKey)) {
            if (actionStatusMap.get(actionKey).equals(RxActionStatus.STARTED)) {
                throw new Exception("Action: " +actionKey+ " is in progress !!!");
            }
        }
        else {
            System.out.println("Action: "+actionKey+" not exist in actionStatusMap.");
        }

    }

    public boolean isTradeActionPossibility(RxInstrument instrument) {
        System.out.println("ACTION_CONTROLLER>isTradeActionPossibility "+instrument.getSymbol());

        List<RxActionType> optActionStatus =  actionStatusMap.entrySet()
                       .stream()
                       .filter(i -> instrument.getFixSymbol().equals( i.getKey().instrumentFixSymbol))
                       .filter(a -> !RxActionStatus.FINISHED.equals(a.getValue()))
                       .map(a -> a.getKey().getAction())
                       .collect(Collectors.toList());

       for (RxActionType a : optActionStatus){
           System.out.println(">>> a:"+a);
          if (   a.equals(RxActionType.BUY_MARKET)
              || a.equals(RxActionType.SELL_MARKET)){
              return false;
              }
       }


       return true;
    }

    public void printActionStatusMap(){
        System.out.println(" -- actionStatusMap --");
       if(!actionStatusMap.isEmpty()) {
           actionStatusMap.forEach((rxActionKey, rxActionStatus) -> System.out.println( "FixSymb: "+
                                                                    rxActionKey.instrumentFixSymbol +" > " +
                                                                    rxActionKey.getAction() + " " +
                                                                    rxActionStatus)
                                   );
           System.out.println("-------------------");

       }
       else {
           System.out.println("> actionStatusMap is empty.");
       }
    }





    }


//TODO PArametr umożliwiający zwiększanie pozycji
//TODO kontrolo czy autotrading
//
//TODO podoban kontrola jak w w terminalu
//  Zwracać info do Trade controlera o statusie i przeprowadzanej akcji .

