package com.fixterminal.shared.dictionaries.brokers;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RxDicBrokers {

    Map<String, RxBroker> brokersMap= new HashMap();

    public RxDicBrokers(){
        System.out.println("Iinit RxDicBrokers ");
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
