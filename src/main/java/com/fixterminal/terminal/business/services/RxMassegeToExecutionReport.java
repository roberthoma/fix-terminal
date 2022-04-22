package com.fixterminal.terminal.business.services;

import com.fixterminal.shared.dictionaries.instruments.RxDicInstruments;
import com.fixterminal.shared.market.RxExecuteReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quickfix.Message;
import quickfix.field.*;

@Service
public class RxMassegeToExecutionReport {

// TODO Encja execut_Report do usunięcia .. bezpośrednio  rozdysponować message

   @Autowired
   RxDicInstruments instrumentsDic;


   public  RxExecuteReport toExecuteReport(Message message){
      RxExecuteReport rxExecuteReport = new RxExecuteReport();
      try {

          ExecType execType = new ExecType();
          message.getField(execType);
          rxExecuteReport.setExecType( RxTypeCastService.fixExecTypeToRxExecType(execType) );

          OrdStatus ordStatus = new OrdStatus();
          message.getField(ordStatus);
          rxExecuteReport.setOrderStatus(RxTypeCastService.fixOrdStatusToRxOrderStatus(ordStatus));

          Symbol symbol = new Symbol();
          message.getField(symbol);
          rxExecuteReport.setInstrument(
                  instrumentsDic.getByFixSymbol(symbol.getValue()));


          OrderID orderID = new OrderID();
          message.getField(orderID);
          rxExecuteReport.setOrderId(orderID.getValue());

          OrdType ordType = new OrdType();
          message.getField(ordType);
          rxExecuteReport.setOrderType(RxTypeCastService.fixOrdTypeToRxOrderType(ordType));

          if (ordType.getValue() == OrdType.LIMIT) { //TODO for develop
              Price price = new Price();
              message.getField(price);
              rxExecuteReport.setPrice(price.getValue());
          }
          else
          if (ordType.getValue() == OrdType.STOP_STOP_LOSS) {
              StopPx stopPx = new StopPx();
              message.getField(stopPx);
              rxExecuteReport.setPrice(stopPx.getValue());
          }

          OrderQty orderQty = new OrderQty();
          message.getField(orderQty);
          rxExecuteReport.setQuantityOrdered(orderQty.getValue());

          Side side = new Side();
          message.getField(side);
          rxExecuteReport.setOrderSide(RxTypeCastService.fixSideToRxOrderSide(side));


          OrigClOrdID origClOrdID = new OrigClOrdID();
          if (message.isSetField(origClOrdID)) {
              message.getField(origClOrdID);
              rxExecuteReport.setOrigClOrdID(origClOrdID.getValue());
          }

          ClOrdID clOrdID = new ClOrdID();
          if (message.isSetField(clOrdID)){
              message.getField(clOrdID);
              rxExecuteReport.setClOrdID(clOrdID.getValue());
          }


      } catch (Exception e) {
          e.printStackTrace();
          return null;
      }

     return rxExecuteReport;
   }



}
