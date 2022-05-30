package com.fixterminal.market.business.trade.orders;


import com.fixterminal.market.business.monitors.RxMonitor;
import com.fixterminal.shared.enumerators.RxActionType;
import com.fixterminal.shared.enumerators.RxOrderSide;
import com.fixterminal.shared.enumerators.RxOrderType;
import com.fixterminal.shared.enumerators.RxPositionDirection;
import com.fixterminal.shared.market.RxMonitorDataVO;
import com.fixterminal.shared.orders.RxOrderEntity;
import com.fixterminal.shared.positions.RxPosition;
import com.fixterminal.market.business.parameters.RxTradeParameters;
import org.springframework.stereotype.Service;

@Service
public class RxOrderFactory {




    public RxOrderEntity cancelLimitOrder(RxActionType marketAction,
                                          RxTradeParameters parameters,
                                          RxMonitor monitor) {
        RxOrderEntity rxOrder = new RxOrderEntity();




        return rxOrder;

    }


    public RxOrderEntity createLimitOrder(RxActionType marketAction,
                                          RxTradeParameters parameters,
                                          RxMonitorDataVO monitorData) {
        RxOrderEntity rxOrder = new RxOrderEntity();
        double limitPrice;

        if (marketAction == RxActionType.SELL_LIMIT) {
            rxOrder.setSide(RxOrderSide.SELL);
            limitPrice = monitorData.marketDataCalcBaseVO.bidBestPrice  + 0.0010;  //TODO PARAMETERS from parameters
        }
        else {
            rxOrder.setSide(RxOrderSide.BUY);
            limitPrice = monitorData.marketDataCalcBaseVO.offerBestPrice - 0.0010;

        }
        rxOrder.setPrice(limitPrice);
         rxOrder.setQuantity(parameters.getQuantity());

        rxOrder.setFixSymbol(monitorData.getInstrument().getFixSymbol());
        rxOrder.setType(RxOrderType.LIMIT);


        return rxOrder;
    }




        public RxOrderEntity createMarketOrder(RxActionType marketAction,
                                               RxTradeParameters parameters,
                                               RxMonitorDataVO monitorData) {

            System.out.println("ORDER_FACTORY>createMarketOrder : "+parameters.getInstrument().getSymbol()+ " " +marketAction);
            RxOrderEntity rxOrder = new RxOrderEntity();
            RxOrderSide side;
            Double quantity;
            String ticketID;

            if (marketAction == RxActionType.CLOSE) {

                RxPosition position = monitorData.getPosition();
                rxOrder.setClOrdID(position.getId());

                if (position.getDirection()  == RxPositionDirection.SHORT) {
                    side = RxOrderSide.BUY;
                } else {
                    side = RxOrderSide.SELL;
                }
                quantity = position.getQuantity();

            }
            else if (marketAction == RxActionType.REVERS) {
                RxPosition position = monitorData.getPosition();

                if (position.getDirection()  == RxPositionDirection.SHORT) {
                    side = RxOrderSide.BUY;
                } else {
                    side = RxOrderSide.SELL;
                }
                quantity = 2 * position.getQuantity();
            }
            else {
                quantity = parameters.getQuantity();

                if (marketAction == RxActionType.SELL_MARKET) {
                      side = RxOrderSide.SELL;
                }
                else if (marketAction == RxActionType.BUY_MARKET) {
                    side = RxOrderSide.BUY;
                }
                else {
                    System.out.println( "!!!!!!!! ERROR order side............");
                    return null; //TODO Do przerobienia
                }
            }

            rxOrder.setQuantity(quantity);
            rxOrder.setSide(side);
            rxOrder.setFixSymbol(monitorData.getInstrument().getFixSymbol());
            rxOrder.setType(parameters.getType());

          return rxOrder;
        }

    public RxOrderEntity createCancelPendingOrder(RxActionType cancelPendingOrder,
                                                  RxTradeParameters tradeParameters,
                                                  RxMonitor monitor)
    {
        RxOrderEntity rxOrder = new RxOrderEntity();



        return rxOrder;
    }
}







