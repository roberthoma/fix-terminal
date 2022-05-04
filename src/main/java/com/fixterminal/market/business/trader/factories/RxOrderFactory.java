package com.fixterminal.market.business.trader.factories;


import com.fixterminal.market.business.monitors.RxMonitor;
import com.fixterminal.shared.enumerators.RxAction;
import com.fixterminal.shared.enumerators.RxOrderSide;
import com.fixterminal.shared.enumerators.RxOrderType;
import com.fixterminal.shared.enumerators.RxPositionDirection;
import com.fixterminal.shared.orders.RxOrderEntity;
import com.fixterminal.shared.positions.RxPosition;
import com.fixterminal.market.business.parameters.RxTradeParameters;
import org.springframework.stereotype.Service;

@Service
public class RxOrderFactory {




    public RxOrderEntity cancelLimitOrder(RxAction marketAction,
                                          RxTradeParameters parameters,
                                          RxMonitor monitor) {
        RxOrderEntity rxOrder = new RxOrderEntity();




        return rxOrder;

    }


    public RxOrderEntity createLimitOrder(RxAction marketAction,
                                          RxTradeParameters parameters,
                                          RxMonitor monitor) {
        RxOrderEntity rxOrder = new RxOrderEntity();
        double limitPrice;

        if (marketAction == RxAction.SELL_LIMIT) {
            rxOrder.setSide(RxOrderSide.SELL);
            limitPrice = monitor.getMarketDataCalcBaseVO().bidBestPrice + 0.0010;  //TODO from parameters
        }
        else {
            rxOrder.setSide(RxOrderSide.BUY);
            limitPrice = monitor.getMarketDataCalcBaseVO().offerBestPrice - 0.0010;

        }
        rxOrder.setPrice(limitPrice);
         rxOrder.setQuantity(parameters.getQuantity());

        rxOrder.setSymbol(monitor.getInstrument().getFixSymbol());
        rxOrder.setType(RxOrderType.LIMIT);


        return rxOrder;
    }




        public RxOrderEntity createMarketOrder(RxAction marketAction,
                                               RxTradeParameters parameters,
                                               RxMonitor monitor) {
            RxOrderEntity rxOrder = new RxOrderEntity();
            RxOrderSide side;
            Double quantity;
            String ticketID;

            if (marketAction == RxAction.CLOSE) {

                RxPosition position = monitor.getPosition();
                rxOrder.setID(position.getId());

                if (position.getDirection()  == RxPositionDirection.SHORT) {
                    side = RxOrderSide.BUY;
                } else {
                    side = RxOrderSide.SELL;
                }
                quantity = position.getQuantity();

            }
            else if (marketAction == RxAction.REVERS) {
                RxPosition position = monitor.getPosition();

                if (position.getDirection()  == RxPositionDirection.SHORT) {
                    side = RxOrderSide.BUY;
                } else {
                    side = RxOrderSide.SELL;
                }
                quantity = 2 * position.getQuantity();
            }
            else {
                quantity = Double.valueOf(parameters.getQuantity());

                if (marketAction == RxAction.SELL_MARKET) {
                      side = RxOrderSide.SELL;
                }
                else if (marketAction == RxAction.BUY_MARKET) {
                    side = RxOrderSide.BUY;
                }
                else {
                    System.out.println( " ERROR order side");
                    return null; //TODO Do przerobienia
                }
            }

            rxOrder.setQuantity(quantity);
            rxOrder.setSide(side);
            rxOrder.setSymbol(monitor.getInstrument().getFixSymbol());
            rxOrder.setType(parameters.getType());

          return rxOrder;
        }

    public RxOrderEntity createCancelPendingOrder(RxAction cancelPendingOrder,
                                                  RxTradeParameters tradeParameters,
                                                  RxMonitor monitor)
    {
        RxOrderEntity rxOrder = new RxOrderEntity();



        return rxOrder;
    }
}







