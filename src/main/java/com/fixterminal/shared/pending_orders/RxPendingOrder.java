package com.fixterminal.shared.pending_orders;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.enumerators.RxOrderSide;
import com.fixterminal.shared.enumerators.RxOrderType;
import lombok.Data;


@Data
public class RxPendingOrder {

    private String uniqueID;      //Unique identifier for Order as assigned by the buy-side
    private String id;
    private RxInstrument instrument;
    private RxOrderSide side ;
    private RxOrderType type ;
    private Double price;
    private Double quantity;

}
