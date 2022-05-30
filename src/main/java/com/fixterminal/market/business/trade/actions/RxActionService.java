package com.fixterminal.market.business.trade.actions;

import com.fixterminal.shared.enumerators.RxActionType;
import com.fixterminal.shared.enumerators.RxOrderSide;
import com.fixterminal.shared.enumerators.RxOrderType;
import com.fixterminal.shared.market.RxExecuteReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


//TODO Do mocnej weryfikacji .............. należy kontrolować np ID #SL#12344
// Gdyż w strategii może być plan gry limitaczy czy stopami

@Slf4j
@Component
public class RxActionService {

    RxActionService(){
    log.info("Init : RxActionService");
    }
    public RxActionType reportToActionType(RxExecuteReport report) {
        System.out.println("ACTION_SERVICE>reportToActionType :"+ report);


        if(report.getOrderSide().equals(RxOrderSide.BUY) &&
           report.getOrderType().equals(RxOrderType.MARKET))
        {
           return  RxActionType.BUY_MARKET;
        }

        if(report.getOrderSide().equals(RxOrderSide.SELL) &&
                report.getOrderType().equals(RxOrderType.MARKET))
        {
            return  RxActionType.SELL_MARKET;
        }



       return RxActionType.STOP_LOSS;

    }
}
