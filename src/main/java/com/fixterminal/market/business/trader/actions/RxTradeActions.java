package com.fixterminal.market.business.trader.actions;

import com.fixterminal.market.business.monitors.RxMonitor;
import com.fixterminal.market.business.monitors.RxMonitorsDesk;
import com.fixterminal.market.business.parameters.RxTradeParametersDesk;
import com.fixterminal.market.ports.RxMessageSenderPort;
import com.fixterminal.shared.dictionaries.instruments.RxDicInstruments;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.enumerators.RxAction;
import com.fixterminal.shared.orders.RxOrderEntity;
import com.fixterminal.market.business.trader.factories.RxOrderFactory;
import com.fixterminal.market.business.parameters.RxTradeParameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RxTradeActions {

    //RxTradeParameters  tradeParameters;
    RxOrderFactory orderFactory;
    RxMonitorsDesk rxMonitorsDesk;
    RxMessageSenderPort messageSender;
    RxTradeParametersDesk  parameterDesk;
    RxDicInstruments instruments;

    @Autowired
    public RxTradeActions(RxOrderFactory orderFactory ,
                          RxMessageSenderPort messageSender,
                          RxMonitorsDesk rxMonitorsDesk,
                          RxTradeParametersDesk  parameterDesk,
                          RxDicInstruments instruments
                           )
   {
      log.info("Init : RxTradeCommands");
      this.orderFactory  = orderFactory;
      this.messageSender = messageSender;
      this.rxMonitorsDesk = rxMonitorsDesk;
      this.parameterDesk = parameterDesk;
      this.instruments = instruments;
    }




    public String actionBuyMarket(RxTradeParameters parameters) {
      try {
          RxMonitor monitor = rxMonitorsDesk.getMonitor(parameters.getInstrument());
          RxOrderEntity order = orderFactory.createMarketOrder(RxAction.BUY_MARKET,
                                                              parameters,
                                                              monitor
                                                              );
          return messageSender
                  .sendNewOrderSingle(order);
      }
      catch (Exception e){
          e.printStackTrace(); // TODO obsługa błędów
          return e.toString();
      }

    }
    public String actionBuyMarket() {
        return actionBuyMarket(parameterDesk.getTradeParameters(instruments.getDefault()));
    }



    public String actionSellMarket(RxTradeParameters parameters) {
        try {
            RxMonitor monitor = rxMonitorsDesk.getMonitor(parameters.getInstrument());
            RxOrderEntity order = orderFactory.createMarketOrder(RxAction.SELL_MARKET,
                    parameters,
                    monitor
            );
            return messageSender
                    .sendNewOrderSingle(order);
        }
        catch (Exception e){
            e.printStackTrace(); // TODO obsługa błędów
            return e.toString();
        }

    }
    public String actionSellMarket() {
        return actionSellMarket(parameterDesk.getTradeParameters(instruments.getDefault()));
    }

    public String actionClosePosition(RxInstrument instrument) {
        try {
            RxOrderEntity order = orderFactory.createMarketOrder(RxAction.CLOSE,
                                                                parameterDesk.getTradeParameters(instrument),
                                                                rxMonitorsDesk.getMonitor(instrument)
                                                                );
            return messageSender.sendNewOrderSingle(order);
        }
        catch (Exception e){
            e.printStackTrace(); // TODO obsługa błędów
            return e.toString();
        }

    }
    public String actionClosePosition() {
        return actionClosePosition(instruments.getDefault());
    }


//    public void actionReverse() throws Exception {
//        RxOrderEntity order = RxOrderFactory.getInstance()
//                .createMarketOrder(RxActionOnMarket.REVERS,tradeParameters,monitor);
//        RxFixTerminal.getInstance()
//                .sendNewOrderSingle(order);
//    }
//
//    public void actionClosePosition() throws Exception {
//
//        RxOrderEntity order = RxOrderFactory.getInstance()
//                .createMarketOrder(RxActionOnMarket.CLOSE,tradeParameters,monitor);
//        RxFixTerminal.getInstance()
//                .sendNewOrderSingle(order);
//    }
//
//    public void actionBuyLimit() throws Exception {
//        RxOrderEntity order = RxOrderFactory.getInstance()
//                .createLimitOrder(RxActionOnMarket.BUY_LIMIT
//                                 ,tradeParameters
//                                 ,monitor);
//        RxFixTerminal.getInstance()
//                .sendNewOrderSingle(order);
//
//    }
//
//    public void actionSellLimit() throws Exception {
//        RxOrderEntity order = RxOrderFactory.getInstance()
//                .createLimitOrder(RxActionOnMarket.SELL_LIMIT,tradeParameters,monitor);
//        RxFixTerminal.getInstance()
//                .sendNewOrderSingle(order);
//    }
//
////TODO poniższy kod jest zdublowaniem merytoryki z RxOrderStopFactory.createStopOrder
//
//    public void actionBreakEvenSet() throws Exception {
//
//        RxPendingOrder pendingOrder  = monitor.getStopLossOrder();
//        RxPosition position =  monitor.getPosition();
//
//        if (pendingOrder != null && position != null ){
//            if(position.getDirection().equals(RxPositionDirection.LONG)){
//                pendingOrder.setPrice( position.getEntryPrice() + tradeParameters.getBREAKEVEN_PROFIT());
//            }else {
//                pendingOrder.setPrice( position.getEntryPrice() - tradeParameters.getBREAKEVEN_PROFIT());
//            }
//            RxRequestMessageSender.getInstance().sendOrderReplaceRequest(pendingOrder);
//            return;
//        }
//
//        RxOrderEntity order = RxOrderStopFactory
//                             .createStopOrder(  RxActionOnMarket.BREAK_EVENT_SET,
//                                                tradeParameters,
//                                                monitor);
//        if (order != null) {
//            RxFixTerminal.getInstance()
//                    .sendNewOrderSingle(order);
//        }
//
//    }
//    //-------------------------------------------------
//
//
//    public void actionSetStopLossUpDown(RxActionOnMarket actionOnMarket )
//               throws Exception {
////TODO set by progress ... algorytm zgbliżający limit do marketu
//        RxPendingOrder order  = monitor.getStopLossOrder();
//
//        if (actionOnMarket == RxActionOnMarket.STOP_LOSS_DOWN) {
//            order.setPrice(order.getPrice() - 0.0001);
//        }
//
//        if (actionOnMarket == RxActionOnMarket.STOP_LOSS_UP) {
//            order.setPrice(order.getPrice() + 0.0001);
//        }
//
//        RxRequestMessageSender.getInstance().sendOrderReplaceRequest(order);
//    }
//
//
//    public void cancelPendingOrder(String uniqueID) {
//
//        RxRequestMessageSender.getInstance().sendOrderCancelRequest(uniqueID);
//    }
//
//
//    public void actionSetStopLoss() throws Exception {
//        RxOrderEntity order = RxOrderStopFactory
//                .createStopOrder(  RxActionOnMarket.STOP_LOSS_SET,
//                        tradeParameters,
//                        monitor);
//        if (order != null) {
//            RxFixTerminal.getInstance()
//                    .sendNewOrderSingle(order);
//        }
//    }
//
//    public void updateStopLossQuantity(RxPendingOrder orderSL, Double quantity) {
//        orderSL.setQuantity(quantity);
//        RxRequestMessageSender.getInstance().sendOrderReplaceRequest(orderSL);
//
//    }
//
//


}
