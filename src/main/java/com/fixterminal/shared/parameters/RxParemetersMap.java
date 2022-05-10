package com.fixterminal.shared.parameters;


import java.util.HashMap;
import java.util.Map;

public class RxParemetersMap extends HashMap<RxParameterDesc, RxParameterValue<?>>
   implements Map<RxParameterDesc, RxParameterValue<?>>
{
   public Map<String, String> toStringMap(){
     Map<String, String> sMap = new HashMap<>();
     forEach((rxParameterDesc, rxParameterValue) ->
                                    sMap.put(rxParameterDesc.getSymbol(),
                                             rxParameterValue.getValue().toString()));
     return sMap;
   }


}
