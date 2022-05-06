package com.fixterminal.terminal.adapters;

import com.fixterminal.market.ports.RxMessageSenderPort;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.orders.RxOrderEntity;
import com.fixterminal.shared.pending_orders.RxPendingOrder;
import com.fixterminal.terminal.business.senders.RxOrderMessageSender;
import com.fixterminal.terminal.business.senders.RxRequestMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RxMessageSenderAdapter implements RxMessageSenderPort {

   RxRequestMessageSender rxRequestMessageSender;
   RxOrderMessageSender   rxOrderMessageSender;

   @Autowired
   public RxMessageSenderAdapter(RxRequestMessageSender rxRequestMessageSender,
                                 RxOrderMessageSender   rxOrderMessageSender){
        this.rxRequestMessageSender = rxRequestMessageSender;
        this.rxOrderMessageSender   = rxOrderMessageSender;
   }

   @Override
   public void  sendMarketDataRequest(RxInstrument instrument){
       rxRequestMessageSender.sendMarketDataRequest(instrument);
   }

    @Override
    public String sendNewOrderSingle(RxOrderEntity rxOrder) {
    try {
        return rxOrderMessageSender.sendNewOrderSingle(rxOrder);
    }
    catch (Exception e){
        e.printStackTrace();
     }
     return null;
    }

    @Override
    public void sendRequestForPositions() {
        rxRequestMessageSender.sendRequestForPositions();
    }

    @Override
    public void sendOrderReplaceRequest(RxPendingOrder orderSL) {
        rxRequestMessageSender.sendOrderReplaceRequest(orderSL);
    }

}
