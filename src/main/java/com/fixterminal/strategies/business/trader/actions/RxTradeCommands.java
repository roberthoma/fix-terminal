package com.fixterminal.strategies.business.trader.actions;

import com.fixterminal.shared.enumerators.RxActionOnMarket;
import com.fixterminal.strategies.business.factories.RxOrderFactory;
import com.fixterminal.strategies.business.parameters.RxTradeParameters;
import com.fixterminal.strategies.business.parameters.RxTradeParametersDesk;
import com.fixterminal.strategies.ports.RxMarketMonitorStrategiesPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RxTradeCommands  {

    //RxTradeParameters  tradeParameters;
    RxOrderFactory orderFactory;
    RxMarketMonitorStrategiesPort monitor;

    RxTradeParametersDesk tradeParametersDesk;

//    public RxTradeCommands(RxMarketMonitorStrategiesPort monitor)
    public RxTradeCommands()
   {
      log.info("Init : RxTradeCommands");
//        this.monitor = monitor;
//        this.tradeParametersDesk = tradeParametersDesk;
      //  tradeParameters = new RxTradeParameters(); ????
    }

//    public String actionBuyMarket() throws Exception {
//
//        RxTradeParameters parameters =   tradeParametersDesk.getTradeParameters(
//
//        RxOrderEntity order =
//        orderFactory.createMarketOrder(RxActionOnMarket.BUY_MARKET,
//                                   tradeParameters,
//                                   monitor);
//        return RxFixTerminal.getInstance()
//                  .sendNewOrderSingle(order);
//    }

//    public void actionSellMarket() throws Exception {
//
//        RxOrderEntity order = RxOrderFactory.getInstance()
//                .createMarketOrder(RxActionOnMarket.SELL_MARKET,tradeParameters,monitor);
//        RxFixTerminal.getInstance()
//                .sendNewOrderSingle(order);
//    }
//
//
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
