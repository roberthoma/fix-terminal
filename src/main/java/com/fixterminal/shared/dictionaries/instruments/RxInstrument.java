package com.fixterminal.shared.dictionaries.instruments;


import com.fixterminal.shared.dictionaries.brokers.RxBroker;
import lombok.Data;

@Data
public class RxInstrument {

    private String   symbol;
    private String   fixSymbol;
    private RxBroker broker;
    private int      pipPosition;

    public RxInstrument(){

    }

    public RxInstrument(String symbol, String fixSymbol, RxBroker broker, int pipPosition) {
        this.symbol = symbol;
        this.fixSymbol = fixSymbol;
        this.broker = broker;
        this.pipPosition = pipPosition;
    }

}
