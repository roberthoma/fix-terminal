package com.fixterminal.shared.dictionaries.instruments;

import java.util.HashMap;
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

}
