package com.fixterminal.terminal.domain.factories;

import com.fixterminal.shared.enumerators.RxOrderType;
import com.fixterminal.shared.orders.RxOrderEntity;
import com.fixterminal.terminal.domain.services.RxTypeCastService;
import org.springframework.stereotype.Service;
import quickfix.field.*;

@Service
public class RxQFixNewOrderSingleFactory {

//    public static  quickfix.Message populateOrder(RxOrderEntity rxOrder, quickfix.Message newOrderSingle) {
//
//        RxOrderType type = rxOrder.getType();
//
//        if (type == RxOrderType.LIMIT)
//            newOrderSingle.setField(new Price(rxOrder.getPrice()));
//        else if (type == RxOrderType.STOP) {
//            newOrderSingle.setField(new StopPx(rxOrder.getPrice()));
//
//        } else if (type == RxOrderType.STOP_LIMIT) {
//            newOrderSingle.setField(new Price(rxOrder.getPrice()));
//            newOrderSingle.setField(new StopPx(rxOrder.getPrice()));
//        }
//
////        if (order.getSide() == OrderSide.SHORT_SELL
////                || order.getSide() == OrderSide.SHORT_SELL_EXEMPT) {
////            newOrderSingle.setField(new LocateReqd(false));
////        }
//
//        return newOrderSingle;
//    }
//

    public static quickfix.fix44.NewOrderSingle create(RxOrderEntity rxOrder) throws Exception{
        System.out.println("rxOrder.getType()="+rxOrder.getType());


        quickfix.fix44.NewOrderSingle newOrderSingle = new quickfix.fix44.NewOrderSingle();


        newOrderSingle.set(new ClOrdID(rxOrder.getID()));
        newOrderSingle.set(RxTypeCastService.rxOrderSideToFixSide(rxOrder.getSide()));
        newOrderSingle.set(new TransactTime());
        newOrderSingle.set(RxTypeCastService.rxOrderTypeToFixOrdType(rxOrder.getType()));

        newOrderSingle.set(new OrderQty(rxOrder.getQuantity()));
        newOrderSingle.set(new Symbol(rxOrder.getSymbol()));

        if (rxOrder.getType().equals(RxOrderType.LIMIT)) {

            newOrderSingle.set(new Price(rxOrder.getPrice()));
        }
        else if (rxOrder.getType().equals(RxOrderType.STOP)) {

            newOrderSingle.set(new StopPx(rxOrder.getPrice()));
        }

        return newOrderSingle;
    }
}


