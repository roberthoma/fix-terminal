package com.fixterminal.app.shared.dictionaries.brokers;
import lombok.Data;

@Data
public class RxBroker {

    double id;
    String name;
    String symbol;

    public RxBroker(double id, String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
        this.id = id;

    }
}
