package com.fixterminal.market.business.trader.actions;

import com.fixterminal.market.business.monitors.RxMonitor;
import com.fixterminal.market.business.monitors.RxMonitorsDesk;
import com.fixterminal.market.business.parameters.RxTradeParametersDesk;
import com.fixterminal.market.business.trader.factories.RxOrderStopFactory;
import com.fixterminal.market.ports.RxMessageSenderPort;
import com.fixterminal.shared.dictionaries.instruments.RxDicInstruments;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.enumerators.RxAction;
import com.fixterminal.shared.orders.RxOrderEntity;
import com.fixterminal.market.business.trader.factories.RxOrderFactory;
import com.fixterminal.market.business.parameters.RxTradeParameters;
import com.fixterminal.shared.pending_orders.RxPendingOrder;
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
          RxMonitor monitor = rxMonitorsDesk.getMonitor(parameters.getInstrument());
          RxOrderEntity order = orderFactory.createMarketOrder(RxAction.BUY_MARKET,
                                                              parameters,
                                                              monitor
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


    public String actionSetStopLoss(RxInstrument instrument) {
      try {
          RxOrderEntity order = orderStopFactory
                  .createStopOrder(RxAction.STOP_LOSS_SET,
                          parameterDesk.getTradeParameters(instrument),
                          rxMonitorsDesk.getMonitor(instrument));
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




}
