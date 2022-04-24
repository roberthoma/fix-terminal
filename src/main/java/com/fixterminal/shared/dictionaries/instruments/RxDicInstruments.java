package com.fixterminal.shared.dictionaries.instruments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RxDicInstruments {

    Map<String, RxInstrument> instrumentMap = new HashMap();



    public void add(RxInstrument instrument){
        instrumentMap.put(instrument.getFixSymbol(),instrument);
    }

    public RxInstrument getByFixSymbol(String fixSymbol){
        if( instrumentMap.containsKey(fixSymbol)) {
            return instrumentMap.get(fixSymbol);
        }

        System.out.println("ERROR: Instrument not found  ,symbol "+fixSymbol);

        return null;
    }

    public List<RxInstrument> toList(){
        List<RxInstrument>  instrumentList = new ArrayList<>();

        instrumentMap.forEach((s, rxInstrument) -> instrumentList.add(rxInstrument));
        //s, rxBroker) ->  brokersList.add( rxBroker));

        return instrumentList;
    }


    public List<RxInstrument> getMonitoredList() {
        List<RxInstrument>  instrumentList = new ArrayList<>();
        instrumentList.add( instrumentMap.get("1"));  //TODO tymczasowa lista .. narazie EURUSD
        return instrumentList;
    }


}
