package com.fixterminal.market.business.trade.strategy;


import com.fixterminal.market.business.monitors.RxMonitor;
import com.fixterminal.market.business.parameters.RxTradeParameters;
import com.fixterminal.market.business.trade.actions.RxActionStopLossController;
import com.fixterminal.market.business.trade.actions.RxActions;
import com.fixterminal.market.business.trade.actions.RxActionsManager;
import com.fixterminal.shared.market.RxMonitorDataVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Scope("prototype")
public class RxStrategyController extends Thread {

    private static final Logger log = LoggerFactory.getLogger(RxStrategyController.class);

    RxMonitor monitor;
    RxTradeParameters tradeParameters;
    @Autowired
    RxActions actions;
    @Autowired
    RxActionStopLossController stopLossController;

    @Autowired
    RxActionsManager actionManager;


    public void setMonitor(RxMonitor monitor){
        this.monitor = monitor;
        monitor.addMarketDataConsumer(this::tradeController);

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

    private void tradeController(RxMonitorDataVO data)  {
      actionManager.printActionStatusMap();

      stopLossController.controlAction(data,tradeParameters); //Pomimo braku pozycjy uruchamiam bo mooże sa SL do anulowania
                                                               // (Temtat do rowinięcia)

      //strategy.trade();

      //To jest strategia typu Random :)
      if( tradeParameters.isAutoTradeOn()) {
           System.out.println("isOpenPosition = "+data.isOpenPosition());
           if(actionManager.isTradeActionPossibility(tradeParameters.getInstrument())){
              if (!data.isOpenPosition()) {
                      Random rand = new Random();
                      int n = rand.nextInt(3);

                      if (n == 1) {
                          actions.actionBuyMarket(tradeParameters);
                      } else if (n == 2) {
                          actions.actionSellMarket(tradeParameters);
                      }

              }
//              else{
//                  System.out.println(data.position);
//              }
          }
      }




// IT WORKS >> it is prototype
        //       RxMarketModel marketModel = m.getMarketModel();
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
