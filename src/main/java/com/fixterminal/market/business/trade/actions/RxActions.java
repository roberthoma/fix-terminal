package com.fixterminal.market.business.trade.actions;

import com.fixterminal.market.business.monitors.RxMonitorsDesk;
import com.fixterminal.market.business.parameters.RxTradeParameterType;
import com.fixterminal.market.business.parameters.RxTradeParametersDesk;
import com.fixterminal.market.business.calculators.RxOrderStopCalculatorService;
import com.fixterminal.market.business.trade.orders.RxOrderStopFactory;
import com.fixterminal.market.business.trade.orders.RxOrdersManager;
import com.fixterminal.market.ports.RxMessageSenderPort;
import com.fixterminal.shared.dictionaries.instruments.RxDicInstruments;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.enumerators.RxActionType;
import com.fixterminal.shared.market.RxMonitorDataVO;
import com.fixterminal.shared.orders.RxOrderEntity;
import com.fixterminal.market.business.trade.orders.RxOrderFactory;
import com.fixterminal.market.business.parameters.RxTradeParameters;
import com.fixterminal.shared.positions.RxPosition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


//TODO obsługa CHANNEL
@Slf4j
@Component
public class RxActions {
    RxOrderFactory orderFactory;
    RxOrderStopFactory orderStopFactory;
    RxMonitorsDesk rxMonitorsDesk;
    RxMessageSenderPort messageSender;
    RxTradeParametersDesk  parameterDesk;
    RxDicInstruments instruments;
    RxActionsManager actionController;
    RxOrdersManager ordersManager;
    @Autowired
    public RxActions(RxOrderFactory orderFactory ,
                     RxOrderStopFactory orderStopFactory,
                     RxMessageSenderPort messageSender,
                     RxMonitorsDesk rxMonitorsDesk,
                     RxTradeParametersDesk  parameterDesk,
                     RxDicInstruments instruments,
                     RxActionsManager actionController,
                     RxOrdersManager ordersManager
                           )
   {
      log.info("Init : RxTradeCommands");
      this.orderFactory  = orderFactory;
      this.orderStopFactory = orderStopFactory;
      this.messageSender = messageSender;
      this.rxMonitorsDesk = rxMonitorsDesk;
      this.parameterDesk = parameterDesk;
      this.instruments = instruments;
      this.actionController = actionController;
      this.ordersManager = ordersManager;

    }


    private void tradeByMarket(RxActionType action, RxTradeParameters parameters)  {
        System.out.println("------------->>> tradeByMarket <<< ------------------------------");
        try {
//            if (actionController.getPositionReportStatus(parameters.getInstrument()).compareTo(RxRequestStatus.RECEIVED) != 0){
//                throw new Exception("Position report doesn't received !!!!");  //TODO do poprawy
//            }

            actionController.check(action,parameters.getInstrument());

            RxMonitorDataVO monitorData = rxMonitorsDesk.getMonitor(parameters.getInstrument()).getData();

            RxOrderEntity order = orderFactory.createMarketOrder(action,parameters,monitorData);

            messageSender.sendNewOrderSingle(order);
            actionController.startAction(action,order);

        }
        catch (Exception e){
            System.out.println(e.getMessage()); // TODO obsługa błędów PROMPTER ????????
        }
        System.out.println("------------->>>END OF tradeByMarket <<< ------------------------------");
    }




    public void actionBuyMarket(RxTradeParameters parameters) {
        tradeByMarket(RxActionType.BUY_MARKET, parameters);
    }
    public void actionBuyMarket(RxInstrument instrument) {
        tradeByMarket(RxActionType.BUY_MARKET, parameterDesk.getTradeParameters(instrument));
    }

    public void actionBuyMarket(Map<String, String> paramsMap) {
        try {
            RxInstrument instrument = instruments.getByFixSymbol(paramsMap.get(RxTradeParameterType.INSTRUMENT.getCmdSymbol()));
            //TODO jeżeli więcej parametrów to zrób kopie parametrów i ustaw te wskazane
            actionBuyMarket(instrument);
        }
        catch (Exception e){
          e.printStackTrace();
        }
    }




    public void actionSellMarket(RxTradeParameters parameters) {
        tradeByMarket(RxActionType.SELL_MARKET, parameters);
    }
    public void actionSellMarket(RxInstrument instrument) {
        tradeByMarket(RxActionType.SELL_MARKET, parameterDesk.getTradeParameters(instrument));
    }

    public void actionSellMarket(Map<String, String> paramsMap) {
        try {
            RxInstrument instrument = instruments.getByFixSymbol(paramsMap.get(RxTradeParameterType.INSTRUMENT.getCmdSymbol()));
            //TODO jeżeli więcej parametrów to zrób kopie parametrów i ustaw te wskazane
            actionSellMarket(instrument);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }




    public String actionClosePosition(RxInstrument instrument) {
        try {
            RxOrderEntity order = orderFactory.createMarketOrder(RxActionType.CLOSE,
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

    public void actionClosePosition(Map<String, String> paramsMap) {
        try {
            RxInstrument instrument = instruments.getByFixSymbol(paramsMap.get(RxTradeParameterType.INSTRUMENT.getCmdSymbol()));
            actionClosePosition(instrument);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public void actionSetStopLoss(RxInstrument instrument) {
      try {

//          if (controller.getPositionReportStatus(instrument).compareTo(RxRequestStatus.RECEIVED) != 0){
//            throw new Exception("Position report doesn't received !!!!");
//          }

          RxOrderEntity order = orderStopFactory
                  .createStopOrder(RxActionType.STOP_LOSS,
                          parameterDesk.getTradeParameters(instrument),
                          rxMonitorsDesk.getMonitor(instrument).getData());

          messageSender.sendNewOrderSingle(order);
          actionController.startAction(RxActionType.STOP_LOSS,order);

      }
      catch (Exception e){
          e.printStackTrace(); // TODO obsługa błędów
      }

    }
//    public String actionSetStopLoss() {
//        return  actionSetStopLoss(instruments.getDefault());
//    }

    public void updateStopLossQuantity(RxOrderEntity orderSL, Double quantity) {
        orderSL.setQuantity(quantity);
        messageSender.sendOrderReplaceRequest(orderSL);

    }


    public void updateStopLossToBreakevent(RxMonitorDataVO data, RxTradeParameters parameters) {
        RxOrderEntity orderSL = ordersManager.getStopLossOrder(parameters.getInstrument());
        RxPosition position = data.getPosition();

        orderSL.setPrice(RxOrderStopCalculatorService.breakevenPriceCalc(position.getDirection(),
                                                                         position.getEntryPrice(),
                                                                         parameters.getBreakeventProfit() ));
        log.info("SEND_ORDER : StopLoss To Breakevent at :"+orderSL.getPrice());
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

    public void updateStopLossPrice(RxOrderEntity orderSL, double newTrailingSLPrice) {

        orderSL.setPrice(newTrailingSLPrice);
        log.info("SEND_ORDER : StopLoss To newTrailingSLPrice ");

        messageSender.sendOrderReplaceRequest(orderSL);

    }
}
