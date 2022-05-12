package com.fixterminal.market.business.trader.actions;

import com.fixterminal.market.business.monitors.RxMonitorsDesk;
import com.fixterminal.market.business.parameters.RxTradeParametersDesk;
import com.fixterminal.market.business.trader.factories.RxOrderStopCalculatorService;
import com.fixterminal.market.business.trader.factories.RxOrderStopFactory;
import com.fixterminal.market.ports.RxMessageSenderPort;
import com.fixterminal.shared.dictionaries.instruments.RxDicInstruments;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.enumerators.RxAction;
import com.fixterminal.shared.market.RxMonitorDataVO;
import com.fixterminal.shared.orders.RxOrderEntity;
import com.fixterminal.market.business.trader.factories.RxOrderFactory;
import com.fixterminal.market.business.parameters.RxTradeParameters;
import com.fixterminal.shared.pending_orders.RxPendingOrder;
import com.fixterminal.shared.positions.RxPosition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RxTradeActions {
    RxOrderFactory orderFactory;
    RxOrderStopFactory orderStopFactory;
    RxMonitorsDesk rxMonitorsDesk;
    RxMessageSenderPort messageSender;
    RxTradeParametersDesk  parameterDesk;
    RxDicInstruments instruments;

    @Autowired
    public RxTradeActions(RxOrderFactory orderFactory ,
                          RxOrderStopFactory orderStopFactory,
                          RxMessageSenderPort messageSender,
                          RxMonitorsDesk rxMonitorsDesk,
                          RxTradeParametersDesk  parameterDesk,
                          RxDicInstruments instruments
                           )
   {
      log.info("Init : RxTradeCommands");
      this.orderFactory  = orderFactory;
      this.orderStopFactory = orderStopFactory;
      this.messageSender = messageSender;
      this.rxMonitorsDesk = rxMonitorsDesk;
      this.parameterDesk = parameterDesk;
      this.instruments = instruments;

    }




    public String actionBuyMarket(RxTradeParameters parameters) {
      try {
          RxMonitorDataVO monitorData = rxMonitorsDesk.getMonitor(parameters.getInstrument()).getData();

          RxOrderEntity order = orderFactory.createMarketOrder(RxAction.BUY_MARKET,
                                                              parameters,
                                                              monitorData
                                                              );
          return messageSender
                  .sendNewOrderSingle(order);
      }
      catch (Exception e){
          e.printStackTrace(); // TODO obsługa błędów PROMPTER ????????
          return e.toString();
      }

    }
    public String actionBuyMarket() {
        return actionBuyMarket(parameterDesk.getTradeParameters(instruments.getDefault()));
    }



    public String actionSellMarket(RxTradeParameters parameters) {
        try {
            RxMonitorDataVO monitorData = rxMonitorsDesk.getMonitor(parameters.getInstrument()).getData();
            RxOrderEntity order = orderFactory.createMarketOrder(RxAction.SELL_MARKET,
                    parameters,
                    monitorData
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
                                                                rxMonitorsDesk.getMonitor(instrument).getData()
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


    public String actionSetStopLoss(RxInstrument instrument) {
      try {
          RxOrderEntity order = orderStopFactory
                  .createStopOrder(RxAction.STOP_LOSS_SET,
                          parameterDesk.getTradeParameters(instrument),
                          rxMonitorsDesk.getMonitor(instrument).getData());
         return order != null ? messageSender.sendNewOrderSingle(order) : null;
      }
      catch (Exception e){
          e.printStackTrace(); // TODO obsługa błędów
          return e.toString();
      }

    }
    public String actionSetStopLoss() {
        return  actionSetStopLoss(instruments.getDefault());
    }

    public void updateStopLossQuantity(RxPendingOrder orderSL, Double quantity) {
        orderSL.setQuantity(quantity);
        messageSender.sendOrderReplaceRequest(orderSL);

    }


    public void updateStopLossToBreakevent(RxMonitorDataVO data, RxTradeParameters parameters) {
        RxPendingOrder orderSL = data.getStopLossOrder();
        RxPosition position = data.getPosition();

        orderSL.setPrice(RxOrderStopCalculatorService.breakevenPriceCalc(position.getDirection(),
                                                                         position.getEntryPrice(),
                                                                         parameters.getBreakeventProfit() ));
        log.info("SEND_ORDER : StopLoss To Breakevent ");
        messageSender.sendOrderReplaceRequest(orderSL);


    }

//    public void updateStopLossByTrailing(RxMonitorDataVO data, RxTradeParameters tradeParameters) {
//
//        RxPendingOrder orderSL = data.getStopLossOrder();
//        RxPosition position = data.getPosition();
//
//        orderSL.setPrice(RxOrderStopCalculatorService.trailingStopLossPriceCalc(
//
//
//                );
//         position.getDirection(),
//                position.getEntryPrice(),
//                parameters.getBreakeventProfit() ));
//        log.info("SEND_ORDER : StopLoss To Breakevent ");
//        messageSender.sendOrderReplaceRequest(orderSL);
//
//    }

    public void updateStopLossPrice(RxPendingOrder orderSL, double newTrailingSLPrice) {

        orderSL.setPrice(newTrailingSLPrice);
        log.info("SEND_ORDER : StopLoss To newTrailingSLPrice ");

        messageSender.sendOrderReplaceRequest(orderSL);

    }
}
