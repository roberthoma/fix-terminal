package com.fixterminal.strategies.business.factories;


import com.fixterminal.shared.enumerators.RxActionOnMarket;
import com.fixterminal.shared.enumerators.RxOrderSide;
import com.fixterminal.shared.enumerators.RxOrderType;
import com.fixterminal.shared.enumerators.RxPositionDirection;
import com.fixterminal.shared.orders.RxOrderEntity;
import com.fixterminal.shared.positions.RxPosition;
import com.fixterminal.strategies.business.parameters.RxTradeParameters;
import com.fixterminal.strategies.ports.RxMarketMonitorTraderPort;

public class RxOrderFactory {




    public RxOrderEntity cancelLimitOrder(RxActionOnMarket marketAction,
                                          RxTradeParameters parameters,
                                          RxMarketMonitorTraderPort monitor) {
        RxOrderEntity rxOrder = new RxOrderEntity();




        return rxOrder;

    }


    public RxOrderEntity createLimitOrder(RxActionOnMarket marketAction,
                                          RxTradeParameters parameters,
                                          RxMarketMonitorTraderPort monitor) {
        RxOrderEntity rxOrder = new RxOrderEntity();
        double limitPrice;

        if (marketAction == RxActionOnMarket.SELL_LIMIT) {
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




        public RxOrderEntity createMarketOrder(RxActionOnMarket marketAction,
                                               RxTradeParameters parameters,
                                               RxMarketMonitorTraderPort monitor) {
            RxOrderEntity rxOrder = new RxOrderEntity();
            RxOrderSide side;
            Double quantity;
            String ticketID;

            if (marketAction == RxActionOnMarket.CLOSE) {

                RxPosition position = monitor.getPosition();
                rxOrder.setID(position.getId());

                if (position.getDirection()  == RxPositionDirection.SHORT) {
                    side = RxOrderSide.BUY;
                } else {
                    side = RxOrderSide.SELL;
                }
                quantity = position.getQuantity();

            }
            else if (marketAction == RxActionOnMarket.REVERS) {
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

                if (marketAction == RxActionOnMarket.SELL_MARKET) {
                      side = RxOrderSide.SELL;
                }
                else if (marketAction == RxActionOnMarket.BUY_MARKET) {
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

    public RxOrderEntity createCancelPendingOrder(RxActionOnMarket cancelPendingOrder,
                                                  RxTradeParameters tradeParameters,
                                                  RxMarketMonitorTraderPort  monitor)
    {
        RxOrderEntity rxOrder = new RxOrderEntity();



        return rxOrder;
    }
}







