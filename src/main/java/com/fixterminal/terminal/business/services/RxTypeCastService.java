package com.fixterminal.terminal.business.services;

import com.fixterminal.shared.base.TwoWayMap;
import com.fixterminal.shared.enumerators.*;
import org.springframework.stereotype.Service;
import quickfix.field.*;

@Service
public class RxTypeCastService {


    static private final TwoWayMap sideMap = new TwoWayMap();
    static private final TwoWayMap orderTypeMap = new TwoWayMap();
    static private final TwoWayMap tifMap = new TwoWayMap();
    static private final TwoWayMap entryTypeMap = new TwoWayMap();
    static private final TwoWayMap marketDataUpdateAction  = new TwoWayMap();
    static private final TwoWayMap execTypeMap = new TwoWayMap();
    static private final TwoWayMap ordStatusMap = new TwoWayMap();


    public static Side rxOrderSideToFixSide(RxOrderSide rxOrderSide){
        Object obj = sideMap.getFirst(rxOrderSide);
        return  (Side)obj;
    }

    public static RxOrderSide fixSideToRxOrderSide(Side side){
        Object obj = sideMap.getSecond(side);
        return  (RxOrderSide)obj;
    }


    public static OrdType rxOrderTypeToFixOrdType(RxOrderType type) {
        return  (OrdType) orderTypeMap.getFirst(type);
    }

    public static RxOrderType fixOrdTypeToRxOrderType(OrdType type) {
        return  (RxOrderType) orderTypeMap.getSecond(type);
    }


    public static RxEntryType fixEntryTypeToRxEntryType(MDEntryType mdEntryType) {
        return (RxEntryType) entryTypeMap.getSecond(mdEntryType);
    }

    public static RxExecType   fixExecTypeToRxExecType(ExecType execType){
        return (RxExecType) execTypeMap.getSecond(execType);
    }

    public static RxOrderStatus fixOrdStatusToRxOrderStatus(OrdStatus ordStatus) {
        return (RxOrderStatus) ordStatusMap.getSecond(ordStatus);
    }


    //TODO do przerobie na funkcje 'of' w klasach ENUM
    static {
        sideMap.put(RxOrderSide.BUY, new Side(Side.BUY));
        sideMap.put(RxOrderSide.SELL, new Side(Side.SELL));
        // sideMap.put(OrderSide.SHORT_SELL, new Side(Side.SELL_SHORT));
        // sideMap.put(OrderSide.SHORT_SELL_EXEMPT, new Side(Side.SELL_SHORT_EXEMPT));
        // sideMap.put(OrderSide.CROSS, new Side(Side.CROSS));
        // sideMap.put(OrderSide.CROSS_SHORT, new Side(Side.CROSS_SHORT));

        orderTypeMap.put(RxOrderType.MARKET, new OrdType(OrdType.MARKET));
        orderTypeMap.put(RxOrderType.LIMIT, new OrdType(OrdType.LIMIT));
        orderTypeMap.put(RxOrderType.STOP, new OrdType(OrdType.STOP_STOP_LOSS));
        orderTypeMap.put(RxOrderType.STOP_LIMIT, new OrdType(OrdType.STOP_LIMIT));

        tifMap.put(RxOrderTimeInForce.DAY, new TimeInForce(TimeInForce.DAY));
        tifMap.put(RxOrderTimeInForce.IOC, new TimeInForce(TimeInForce.IMMEDIATE_OR_CANCEL));
        tifMap.put(RxOrderTimeInForce.OPG, new TimeInForce(TimeInForce.AT_THE_OPENING));
        tifMap.put(RxOrderTimeInForce.GTC, new TimeInForce(TimeInForce.GOOD_TILL_CANCEL));
        tifMap.put(RxOrderTimeInForce.GTX, new TimeInForce(TimeInForce.GOOD_TILL_CROSSING));

        entryTypeMap.put(RxEntryType.BID, new MDEntryType(MDEntryType.BID));
        entryTypeMap.put(RxEntryType.OFFER, new MDEntryType(MDEntryType.OFFER));

        marketDataUpdateAction.put(RxMDUpdateActionEnum.NEW, new MDUpdateAction(MDUpdateAction.NEW));
        marketDataUpdateAction.put(RxMDUpdateActionEnum.CHANGE, new MDUpdateAction(MDUpdateAction.CHANGE));
        marketDataUpdateAction.put(RxMDUpdateActionEnum.DELETE, new MDUpdateAction(MDUpdateAction.DELETE));

        execTypeMap.put(RxExecType.NEW, new ExecType(ExecType.NEW));
        execTypeMap.put(RxExecType.ORDER_STATUS , new ExecType(ExecType.ORDER_STATUS));
        execTypeMap.put(RxExecType.CANCEL , new ExecType(ExecType.CANCELED));
        execTypeMap.put(RxExecType.REPLACED , new ExecType(ExecType.REPLACED));
        execTypeMap.put(RxExecType.TRADE , new ExecType(ExecType.TRADE));

        ordStatusMap.put(RxOrderStatus.NEW, new OrdStatus(OrdStatus.NEW));
        ordStatusMap.put(RxOrderStatus.CANCELED, new OrdStatus(OrdStatus.CANCELED));
        ordStatusMap.put(RxOrderStatus.FILLED, new OrdStatus(OrdStatus.FILLED));

    }

}
