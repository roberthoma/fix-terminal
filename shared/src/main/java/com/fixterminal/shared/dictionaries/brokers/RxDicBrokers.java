package com.fixterminal.shared.dictionaries.brokers;

import java.util.HashMap;
import java.util.Map;

public class RxDicBrokers {

    Map<String, RxBroker> brokersMap= new HashMap();

    public void add(RxBroker broker){
        brokersMap.put(broker.getSymbol(),broker);
    }

}
