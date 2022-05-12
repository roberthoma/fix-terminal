package com.fixterminal.market.business.trader.factories;

import com.fixterminal.shared.enumerators.RxAction;
import com.fixterminal.shared.enumerators.RxOrderSide;
import com.fixterminal.shared.enumerators.RxOrderType;
import com.fixterminal.shared.enumerators.RxPositionDirection;
import com.fixterminal.shared.market.RxMonitorDataVO;
import com.fixterminal.shared.orders.RxOrderEntity;
import com.fixterminal.shared.positions.RxPosition;
import com.fixterminal.market.business.parameters.RxTradeParameters;
import org.springframework.stereotype.Service;

@Service
public class RxOrderStopFactory {



    private void setStopLossOrder(RxOrderEntity order,
                                         RxPosition position,
                                         RxTradeParameters parameters

    )  {

        //TODO sprawdzenie minimum stanu rynku
        if(position.getDirection().equals(RxPositionDirection.LONG)){
            order.setPrice( position.getEntryPrice() - parameters.getStopLossDistance());
        }else {
            order.setPrice( position.getEntryPrice() + parameters.getStopLossDistance());
        }

    }

   private void setBreakEvenOrder(RxOrderEntity order,
                                         RxPosition position,
                                         RxTradeParameters parameters

)  {

//  //TODO sprawdzenie minimum stanu rynku
//      if(position.getDirection().equals(RxPositionDirection.LONG)){
//         order.setPrice( position.getEntryPrice() + parameters.getBreakeventProfit());
//      }else {
//          order.setPrice( position.getEntryPrice() - parameters.getBreakeventProfit());
//      }

       order.setPrice(RxOrderStopCalculatorService.breakevenPriceCalc(position.getDirection(),
                                                                      position.getEntryPrice(),
                                                                      parameters.getBreakeventProfit())
                                                                      );
   }

    private void setSide (RxOrderEntity order,RxPosition position){

        if(position.getDirection().equals(RxPositionDirection.LONG)){
            order.setSide(RxOrderSide.SELL);
        }else {
            order.setSide(RxOrderSide.BUY);
        }

    }


    public  RxOrderEntity createStopOrder(RxAction marketAction,
                                                 RxTradeParameters parameters,
                                                 RxMonitorDataVO monitorData)
   {
        RxOrderEntity rxOrder;
        RxPosition position;

        rxOrder = new RxOrderEntity();


        if (marketAction.equals(RxAction.BREAK_EVENT_SET)
        || marketAction.equals(RxAction.STOP_LOSS_SET)
           ) {

            if (!monitorData.isOpenPosition()) {
                System.out.println("NO position for action " + marketAction);
                return null;
            }
            position = monitorData.getPosition();


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
            rxOrder.setSymbol(monitorData.instrument.getFixSymbol());
            rxOrder.setType(RxOrderType.STOP);
        }


        return rxOrder;
    }

}
