package com.fixterminal.strategies.business.parameters;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.enumerators.RxOnOff;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class RxTradeParametersDesk {

   Map<RxInstrument, RxTradeParameters> parametersMap = new HashMap<>();



   public void initParameters(RxInstrument instrument){
      if (!parametersMap.containsKey(instrument)){
         RxTradeParameters parameters =  RxTradeParametersFactory.create (instrument);

         parametersMap.put(instrument, parameters);

         System.out.println("> RxTradeParametersDesk  "+instrument.getSymbol()+" \n params=" + parameters.toString());
      }
   }


   public void setAutoTradingOff() {
      parametersMap.forEach((instrument, rxTradeParameters) -> rxTradeParameters.setAUTO_TRADE(RxOnOff.OFF));
   }


   public RxTradeParameters getTradeParameters(RxInstrument instrument){
     return parametersMap.get(instrument);
   }

}
