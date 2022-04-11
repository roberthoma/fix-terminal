package com.fixterminal.shared.base;


import com.fixterminal.shared.market.RxExecuteReport;
import com.fixterminal.shared.pending_orders.RxPendingOrder;

public class RxExecuteReportService {

  public static RxPendingOrder castToPendingOrder(RxExecuteReport rxExecuteReport){
   RxPendingOrder rxPendingOrder  = new RxPendingOrder();

      rxPendingOrder.setId(rxExecuteReport.getOrderId());
      rxPendingOrder.setInstrument(rxExecuteReport.getInstrument());
      rxPendingOrder.setPrice(rxExecuteReport.getPrice()) ;
      rxPendingOrder.setSide(rxExecuteReport.getOrderSide());
      rxPendingOrder.setType(rxExecuteReport.getOrderType());
      rxPendingOrder.setQuantity(rxExecuteReport.getQuantityOrdered());
      rxPendingOrder.setUniqueID(rxExecuteReport.getClOrdID());

//      System.out.println("EXECURE_REPO=>"+rxExecuteReport+"<");
//      System.out.println("PENDING_ORD=>"+rxPendingOrder+"<");

    return rxPendingOrder;
}
}
