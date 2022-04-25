package com.fixterminal.strategies.business.factories;

import com.fixterminal.shared.enumerators.RxActionOnMarket;
import com.fixterminal.shared.enumerators.RxOrderSide;
import com.fixterminal.shared.enumerators.RxOrderType;
import com.fixterminal.shared.enumerators.RxPositionDirection;
import com.fixterminal.shared.orders.RxOrderEntity;
import com.fixterminal.shared.positions.RxPosition;
import com.fixterminal.strategies.business.parameters.RxTradeParameters;
import com.fixterminal.strategies.ports.RxMarketMonitorStrategiesPort;

public class RxOrderStopFactory {



    private static void setStopLossOrder(RxOrderEntity order,
                                         RxPosition position,
                                         RxTradeParameters parameters

    )  {

        //TODO sprawdzenie minimum stanu rynku
        if(position.getDirection().equals(RxPositionDirection.LONG)){
            order.setPrice( position.getEntryPrice() - parameters.getSTOP_LOSS_DISTANCE());
        }else {
            order.setPrice( position.getEntryPrice() + parameters.getSTOP_LOSS_DISTANCE());
        }

    }

   private static void setBreakEvenOrder(RxOrderEntity order,
                                         RxPosition position,
                                         RxTradeParameters parameters

)  {

  //TODO sprawdzenie minimum stanu rynku
      if(position.getDirection().equals(RxPositionDirection.LONG)){
         order.setPrice( position.getEntryPrice() + parameters.getBREAKEVEN_PROFIT());
      }else {
          order.setPrice( position.getEntryPrice() - parameters.getBREAKEVEN_PROFIT());
      }

   }

    private static void setSide (RxOrderEntity order,RxPosition position){

        if(position.getDirection().equals(RxPositionDirection.LONG)){
            order.setSide(RxOrderSide.SELL);
        }else {
            order.setSide(RxOrderSide.BUY);
        }

    }


    public static  RxOrderEntity createStopOrder(RxActionOnMarket marketAction,
                                                 RxTradeParameters parameters,
                                                 RxMarketMonitorStrategiesPort monitor)
   {
        RxOrderEntity rxOrder;
        RxPosition position;

        rxOrder = new RxOrderEntity();


        if (marketAction.equals(RxActionOnMarket.BREAK_EVENT_SET)
        || marketAction.equals(RxActionOnMarket.STOP_LOSS_SET)
           ) {

            if (!monitor.isOpenPosition()) {
                System.out.println("NO position for action " + marketAction);
                return null;
            }
            position = monitor.getPosition();


            switch (marketAction) {
                case STOP_LOSS_SET:
                    setStopLossOrder(rxOrder, position,parameters );
                    break;
                case BREAK_EVENT_SET:
                    setBreakEvenOrder(rxOrder, position,parameters );
                    break;
            }






            setSide(rxOrder, position);

            rxOrder.setQuantity(parameters.getQuantity());
            rxOrder.setSymbol(monitor.getInstrument().getFixSymbol());
            rxOrder.setType(RxOrderType.STOP);
        }


        return rxOrder;
    }

}
