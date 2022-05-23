package com.fixterminal.market.business.trade.controllers;


import com.fixterminal.market.business.monitors.RxMonitor;
import com.fixterminal.market.business.parameters.RxTradeParameters;
import com.fixterminal.market.business.trade.actions.RxTradeActionsController;
import com.fixterminal.shared.enumerators.RxRequestStatus;
import com.fixterminal.shared.market.RxMonitorDataVO;
import com.fixterminal.market.business.trade.actions.RxTradeActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Scope("prototype")
public class RxTradeController extends Thread {

    private static final Logger log = LoggerFactory.getLogger(RxTradeController.class);

    RxMonitor monitor;
    RxTradeParameters tradeParameters;
    @Autowired
    RxTradeActions actions;
    @Autowired
    RxStopLossController stopLossController;

    @Autowired
    RxTradeActionsController actionsController;


    int n_trade = 0; // rodo do usunięcia

    public void setMonitor(RxMonitor monitor){
        this.monitor = monitor;
        monitor.addForEachMsgConsumer(this::tradeControlByEachMsg);

    }

    public void setParameters(RxTradeParameters tradeParameters) {
      this.tradeParameters =  tradeParameters;
        System.out.println("ROHO getBreakeventActivateDistance()> "+ tradeParameters.getBreakeventActivateDistance());
    }



    public void run(){
        log.info("Start RxTradeController thread for : "+monitor.getInstrument().getSymbol());
//TODO Kontrola czasowa ta sama procedura co w tradeControlByEachMsg  lecz wykonywana kiedy w damym czasie nie była wykonana na poziomie
// labdy z monitora
//        do {
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        while (true);
    }

    private void tradeControlByEachMsg(RxMonitorDataVO data)  {
        stopLossController.controlAction(data,tradeParameters); //Pomimo braku pozycjy uruchamiam bo mooże sa SL do anulowania
                                                               // (Temtat do rowinięcia)


// ????????????  Nie działa random .. nie ma zwrotu
      if( tradeParameters.isAutoTradeOn()) {
          if (actionsController.getPositionReportStatus(tradeParameters.getInstrument()).compareTo(RxRequestStatus.RECEIVED) == 0) {
              if (!data.isOpenPosition()) {
                      n_trade++;
                      Random rand = new Random();
                      int n = rand.nextInt(3);

                      if (n == 1) {
                          actions.actionBuyMarket(tradeParameters);
                      } else if (n == 2) {
                          actions.actionSellMarket(tradeParameters);
                      }

              }
              else{
                  System.out.println(data.position);
              }
          }
    //      System.out.println("BRAK HANDLU");
      }
        //       RxMarketModel marketModel = m.getMarketModel();


// IT WORKS >> it is prototype
//            if (marketModel.getMarketState() ==  RxMarketState.OVERSOLD
//            && marketModel.getTrendCondition() == RxMarketTrendCondition.UPTREND)
//            {
//              try {
//                  System.out.println("!!!! > Próba kupna ~~~~ <<<");
//                if (!isBought) {
//                    tradeCmd.actionBuyMarket();
//                    isBought = true;
//                }
//              }
//              catch (Exception e){
//                  System.out.println(e);
//              }
//            }

  //      }

    }


}