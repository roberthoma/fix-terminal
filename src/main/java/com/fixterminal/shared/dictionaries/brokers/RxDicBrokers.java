package com.fixterminal.shared.dictionaries.brokers;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class RxDicBrokers {

    Map<String, RxBroker> brokersMap= new HashMap();

    public RxDicBrokers(){
       log.info("Init : RxDicBrokers ");
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
