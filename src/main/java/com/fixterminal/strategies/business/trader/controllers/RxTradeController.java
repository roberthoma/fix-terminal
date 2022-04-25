package com.fixterminal.strategies.business.trader.controllers;


import com.fixterminal.shared.pending_orders.RxPendingOrder;
import com.fixterminal.shared.positions.RxPosition;
import com.fixterminal.strategies.business.trader.actions.RxTradeCommands;
import com.fixterminal.strategies.ports.RxMarketMonitorStrategiesPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RxTradeController extends Thread {

    private static final Logger log = LoggerFactory.getLogger(RxTradeController.class);



/*-------------------------------------------------
            BREAKEVEN PARAMS
            ----------------
AUTO_BRE_SET : ON/OFF
ACTIVATE_BRE_DIST : 3pips
BREAKEVEN_PROFIT : 0.5 pip

---------------------*/
/*==========================================================
   PARAM FOR NEW ORDER



QUANTITY : 0.01 lot
or
QUANTITY : 1000 eur

SL_ON_OPEN : 2pips
TP_ON_OPEN : 5pips

AUTO_SL : ON/OFF

 */

/*==========================================================
   INFO CALC
ONE_PIP_VALUE : 0,71 eur
SL_VALUE : 0,70 eur
TP_VALUE : 2,30 eur
 */


//Traling STOP
//------------------------------------------------------------


    RxMarketMonitorStrategiesPort monitor;

    RxTradeCommands tradeCmd;
    boolean isBought = false; //tmp

 //   Parameters i przekazanie do  wyszyscich poleceni jako instancji


    public RxTradeController(RxMarketMonitorStrategiesPort monitor){
        log.info("Set autoTR inst ="+monitor.getInstrument()
                                                      .getFixSymbol());

        this.monitor = monitor;
        monitor.setTradeControllerConsumer(m -> tradeControlByMsg());

//        tradeCmd = new RxTradeCommands(monitor);


    }

    public void run(){

        // w zależności czasowej
        do {
//            System.out.println("AUTO TRADIN");
            try {
                Thread.sleep(10000);
                //TODO  controlling : communications  - > NO Concepts
                // TODO  : positions time controller
// TODO monitoramowa każdego przychodzącego zlecenia.
// Zlecenia dziele na : bezpośrednie  na rynek i pośrednie administrowane przez kontroler tradingu
// TODO Interaktywne komendy + / - wilkość  inkremetacja jakieś parametru
//     przesunięcia sl
// TODO ustawienie czasu otwartej pozycji


                //System.out.println("TEST run from  RxTradeController ... ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (true);
    }

    private void tradeControlByMsg()  {

        RxPendingOrder orderSL;
        RxPosition position;
 //       RxMarketModel marketModel = m.getMarketModel();


        if(monitor.isOpenPosition()) {
           try {
               position = monitor.getPosition();
               orderSL  = monitor.getStopLossOrder();

//             >>>> Kontrola  i ustawienie stusu otwartych pozycji
//             ustawieni SL tylko na poz OPEN
//             -  kontrola break event
//             - kontrola traling stopy w zalezności od profitu
//
//             - kontrola do zamknięcia w zalezności marketModel.status




               if (orderSL == null) {
                   System.out.println(" > Setting STOP_LOSS > ");
                   //TMP ...       tradeCmd.actionSetStopLoss();
                   //TODO Czasami wyprzedzam raport i tworzą się dwa zlecenia. SL :(

               } else {

                  if(orderSL.getQuantity().compareTo(position.getQuantity()) != 0) {
                      System.out.println(">>>       StopLoss other QUANTITY >>>> ");
                      System.out.println(">>> position.getQuantity()=" + position.getQuantity());
                      System.out.println(">>> orderSL.getQuantityOrdered()=" + orderSL.getQuantity());

            //TMP ...          tradeCmd.updateStopLossQuantity(orderSL, position.getQuantity());
                   }

                   if (position.getQuantity().compareTo(orderSL.getQuantity()) != 0) {
                   }


               }
            }
           catch (Exception e){
               System.out.println(e);
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
