package com.fixterminal.market.business.parameters;

import com.fixterminal.shared.dictionaries.instruments.RxDicInstruments;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class RxTradeParametersDesk {

   Map<RxInstrument, RxTradeParameters> parametersMap = new HashMap<>();

   RxDicInstruments instruments;

   @Autowired
   RxTradeParametersDesk(RxDicInstruments instruments){
      log.info("Init : RxTradeParametersDesk");
      this.instruments = instruments;
      instruments.toList().forEach(instrument ->
                 parametersMap.put(instrument, RxTradeParametersFactory.create (instrument)));
   }


   public void setAutoTradingOff() {
     parametersMap.forEach((instrument, rxTradeParameters) -> rxTradeParameters.setAutoTradeOff());
   }

   public void setAutoTradingOn() {
     parametersMap.forEach((instrument, rxTradeParameters) -> rxTradeParameters.setAutoTradeOn());
   }


   public RxTradeParameters getTradeParameters(RxInstrument instrument){
     return parametersMap.get(instrument);
   }

}
