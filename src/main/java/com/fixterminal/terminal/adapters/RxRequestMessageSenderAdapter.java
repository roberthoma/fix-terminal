package com.fixterminal.terminal.adapters;

import com.fixterminal.market.ports.RxRequestMessageSenderPort;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.terminal.business.senders.RxRequestMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RxRequestMessageSenderAdapter implements RxRequestMessageSenderPort {

   RxRequestMessageSender rxRequestMessageSender;

   @Autowired
   public RxRequestMessageSenderAdapter(RxRequestMessageSender rxRequestMessageSender){
        this.rxRequestMessageSender = rxRequestMessageSender;
   }

   @Override
   public void  sendMarketDataRequest(RxInstrument instrument){
       rxRequestMessageSender.sendMarketDataRequest(instrument);
   }

}
