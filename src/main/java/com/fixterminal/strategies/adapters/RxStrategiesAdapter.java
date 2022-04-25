package com.fixterminal.strategies.adapters;

import com.fixterminal.api.ports.RxStrategiesPort;
import com.fixterminal.commands.base.RxCommandsEnum;
import com.fixterminal.shared.dictionaries.instruments.RxDicInstruments;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.strategies.business.parameters.RxTradeParametersDesk;
import com.fixterminal.strategies.business.trader.controllers.RxTradeControllersDesk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RxStrategiesAdapter implements RxStrategiesPort {

    RxTradeControllersDesk tradeControllersDesk;
    RxTradeParametersDesk  tradeParametersDesk;
    RxDicInstruments dicInstruments;
    @Autowired
    public RxStrategiesAdapter(RxDicInstruments dicInstruments,
                               RxTradeControllersDesk tradeControllersDesk,
                               RxTradeParametersDesk tradeParametersDesk
    ){
       this.dicInstruments       = dicInstruments;
       this.tradeControllersDesk = tradeControllersDesk;
       this.tradeParametersDesk  = tradeParametersDesk;
    }

    @Override
    public void start() {
        tradeControllersDesk.start();

        //TODO Monitorowanie tylko tych wskazanych które będą monitorowane :)
        //for (RxInstrument instrument : dicInstruments.getMonitoredList())
        for (RxInstrument instrument : dicInstruments.toList())
        {
            tradeParametersDesk.initParameters(instrument);
            //tradeParametersDesk.
        }

//        for (RxInstrument instrument : dicInstruments.toList())
//        {
//            >?>>>>>>>;
//        }



    }


    //TODO Type. imediately : close all open position stc
    @Override
    public void setAutoTradingOff() {
        tradeParametersDesk.setAutoTradingOff();
    }

    @Override
    public RxCommandsEnum commandDecode(String cmd) {
 //   ????

        return null;
    }

}
