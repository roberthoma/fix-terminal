package com.fixterminal.shared.positions;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.enumerators.RxPositionDirection;
import com.fixterminal.shared.enumerators.RxPositionStatus;
import lombok.Data;

@Data
public class RxPosition {

        int idx; // Ordinal Number
        String id ;
        RxInstrument instrument;
        Double entryPrice;
        RxPositionDirection direction;
        Double quantity;

        Double  gross;
        Double  net;
        Double  pips;
        Double  marketDistance;

        RxPositionStatus status;


        //TODO live time : Czas życia pozycja / kalkulowany na bierząco
        // status STOP_LOSS : - ustawione ok
        //  nie ustawione
        // usatwione ale nie powawnie inna wielkowść :(
        //

        public RxPosition(){
           this.status = RxPositionStatus.OPEN;
        }

        @Override
        public String toString(){
          return instrument.getSymbol()+ " "+direction+ "  " + entryPrice + " " + gross;

        }
}
