package com.fixterminal.shared.dictionaries.brokers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RxDicBrokers {

    Map<String, RxBroker> brokersMap= new HashMap();

    public RxDicBrokers(){
        System.out.println("Init RxDicBrokers ");
    }

    public void add(RxBroker broker){
        brokersMap.put(broker.getSymbol(),broker);
    }

    public List<RxBroker> toList(){
       List<RxBroker>  brokersList = new ArrayList<>();

        brokersMap.forEach((s, rxBroker) ->  brokersList.add( rxBroker));

       return brokersList;
    }

}
