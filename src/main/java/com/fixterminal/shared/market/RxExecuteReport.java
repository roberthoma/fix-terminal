package com.fixterminal.shared.market;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.enumerators.RxExecType;
import com.fixterminal.shared.enumerators.RxOrderSide;
import com.fixterminal.shared.enumerators.RxOrderStatus;
import com.fixterminal.shared.enumerators.RxOrderType;
import lombok.Data;

@Data
public class RxExecuteReport {
    String        clOrdID;
    String        origClOrdID;  //Unique identifier for Order as assigned by the buy-side
    String        orderId;
    RxExecType    execType ;
    RxOrderStatus orderStatus;
    RxInstrument  instrument;
    RxOrderType   orderType;
    RxOrderSide   orderSide;
    Double        price;
    Double        quantityOrdered;


}
