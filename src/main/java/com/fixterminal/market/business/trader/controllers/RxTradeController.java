package com.fixterminal.market.business.trader.controllers;


import com.fixterminal.market.business.monitors.RxMonitor;
import com.fixterminal.market.business.parameters.RxTradeParameters;
import com.fixterminal.shared.pending_orders.RxPendingOrder;
import com.fixterminal.shared.positions.RxPosition;
import com.fixterminal.market.business.trader.actions.RxTradeActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class RxTradeController extends Thread {

    private static final Logger log = LoggerFactory.getLogger(RxTradeController.class);

    RxMonitor monitor;
    RxTradeParameters tradeParameters;
    @Autowired
    RxTradeActions actions;


    RxTradeParameters parameters;

    public void setMonitor(RxMonitor monitor){
        this.monitor = monitor;
        monitor.addForEachMsgConsumer(m -> tradeControlByEachMsg());

    }

    public void setParameters(RxTradeParameters tradeParameters) {
      this.tradeParameters =  tradeParameters;
        System.out.println("ROHO getBreakeventActivateDistance()> "+ tradeParameters.getBreakeventActivateDistance());
    }



    public void run(){
        log.info("Start RxTradeController thread for : "+monitor.getInstrument().getSymbol());
//        do {
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        while (true);
    }

    private void tradeControlByEachMsg()  {

        RxPendingOrder orderSL;
        RxPosition position;
 //       RxMarketModel marketModel = m.getMarketModel();


        if(monitor.isOpenPosition()) {
           try {
               position = monitor.getPosition();
               orderSL  = monitor.getStopLossOrder();


               if (orderSL == null) {
                   System.out.println(" > Setting STOP_LOSS > ");
                   actions.actionSetStopLoss();
                   //TODO Czasami wyprzedzam raport i tworzą się dwa zlecenia. SL :(

               } else {

                  if(orderSL.getQuantity().compareTo(position.getQuantity()) != 0) {
                      System.out.println(">>>       StopLoss other QUANTITY >>>> ");
                      System.out.println(">>> position.getQuantity()=" + position.getQuantity());
                      System.out.println(">>> orderSL.getQuantityOrdered()=" + orderSL.getQuantity());

                      actions.updateStopLossQuantity(orderSL, position.getQuantity());
                   }

                   //Breakeven
                   System.out.println("Breakeven POSITION DIST = "+position.getMarketDistance());
                   System.out.println("Breakeven POSITION  = "+tradeParameters.getBreakeventActivateDistance());
                   //TODO kontrola oktulanej SL
                   if (position.getMarketDistance() > tradeParameters.getBreakeventActivateDistance())
                   {
                      actions.updateStopLossToBreakevent(monitor, tradeParameters);
                   }

                   //TRAILG STOP


               }
            }
           catch (Exception e){
               e.printStackTrace();
           }

        }
//        else {

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
