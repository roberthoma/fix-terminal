package com.fixterminal.market.business.trade.actions;

import com.fixterminal.shared.dictionaries.instruments.RxDicInstruments;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.enumerators.RxRequestStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class RxTradeActionsController {

  Map<RxInstrument, RxRequestStatus> positionReportStatusMap = new HashMap<>();
    @Autowired
    RxDicInstruments dicInstruments;


    RxTradeActionsController(){
      log.info("Init : RxTradeActionsController");
    }


//    public void setRxExecuteReportStatus(RxInstrument instrument, RxRequestStatus received) {
//
//    }

    public void setRxPositionReportStatus(RxInstrument instrument, RxRequestStatus status) {
        //System.out.println(">>> getPositionReportStatus>>>> PUT "+instrument.getSymbol() + " "+status);

        positionReportStatusMap.put(instrument,status);
    }
    public void setRxPositionReportStatus(RxRequestStatus status) {
        for (RxInstrument instrument : dicInstruments.toList())
        {
          //  System.out.println(">>> getPositionReportStatus for ALL>>>> PUT "+instrument.getSymbol() + " "+status);
            positionReportStatusMap.put(instrument,status);
        }


    }



    public RxRequestStatus getPositionReportStatus(RxInstrument instrument) {

        //System.out.println(">>> getPositionReportStatus>>>> size="+positionReportStatusMap.size());

        System.out.println("POSITION STATUS "+instrument.getSymbol()+ " " +
                                              positionReportStatusMap.get(instrument)
                                              );

        return positionReportStatusMap.get(instrument);

    }


//TODO PArametr umożliwiający zwiększanie pozycji
//TODO kontrolo czy autotrading
//
//TODO podoban kontrola jak w w terminalu
//  Zwracać info do Trade controlera o statusie i przeprowadzanej akcji .
}
