package com.fixterminal.terminal.domain.services;

import com.fixterminal.shared.dictionaries.instruments.RxDicInstruments;
import com.fixterminal.shared.enumerators.RxPositionDirection;
import com.fixterminal.shared.positions.RxPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quickfix.Message;
import quickfix.field.NoPositions;
import quickfix.field.PosMaintRptID;
import quickfix.field.SettlPrice;
import quickfix.field.Symbol;
import quickfix.fix44.PositionReport;

@Service
public class RxMessageToPositionService {


    // >> !! NOT IMPL  >>8=FIX.4.4
// 9=153
// 35=AP
// 34=10
// 49=CSERVER
// 50=TRADE
// 52=20210225-14:18:47.348
// 56=icmarkets.3963919
// 55=1
// 710=12z1as
// 721=211635194
// 727=1
// 728=0
// 730=1.22162
// 702=1
// 704=3000
// 705=0
// 10=107
//
    @Autowired
    static
    RxDicInstruments instrumentsDic;


    public static RxPosition toPosition(Message message) {
        RxPosition position = new RxPosition();
        try {

            if (!message.isSetField(NoPositions.FIELD )) {
               return null;
            }

            NoPositions noPositions = new NoPositions();
            //PositionQty group = new PositionQty();

            PositionReport.NoPositions group = new PositionReport.NoPositions();

            message.getField( noPositions);

            message.getGroup(1, group);

            PosMaintRptID idPos = new PosMaintRptID();
            message.getField(idPos);
            position.setId(idPos.getValue());

            position.setQuantity(group.getLongQty().getValue());

            position.setEntryPrice(message.getDouble(SettlPrice.FIELD)); //SettlPrice

            Symbol symbol = new Symbol();
            message.getField(symbol);

            position.setInstrument(instrumentsDic.getByFixSymbol(symbol.getValue()));

            if (group.getLongQty().getValue() > 0.0) {
                position.setDirection(RxPositionDirection.LONG);
                position.setQuantity(group.getLongQty().getValue());
            } else {
                position.setDirection(RxPositionDirection.SHORT);
                position.setQuantity(group.getShortQty().getValue());
            }


            return position;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }



    }
}
