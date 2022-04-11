package com.fixterminal.shared.dictionaries.instruments;


import com.fixterminal.shared.dictionaries.brokers.RxBroker;
import lombok.Data;

@Data
public class RxInstrument {

    private String   name;
    private String   fixSymbol;
    private RxBroker broker;
    private int      pipPosition;

    public RxInstrument(){

    }

    public RxInstrument(String name, String fixSymbol, RxBroker broker, int pipPosition) {
        this.name = name;
        this.fixSymbol = fixSymbol;
        this.broker = broker;
        this.pipPosition = pipPosition;
    }

}
