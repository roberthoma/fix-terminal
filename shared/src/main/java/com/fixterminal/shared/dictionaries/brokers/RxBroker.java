package com.fixterminal.shared.dictionaries.brokers;
import lombok.Data;

@Data
public class RxBroker {

    double id;
    String name;
    String symbol;
    String description;

    public RxBroker(double id,  String symbol, String name, String description ) {
        this.name = name;
        this.symbol = symbol;
        this.id = id;
        this.description = description;

    }
}
