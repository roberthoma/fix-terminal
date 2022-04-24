package com.fixterminal.strategies.adapters;

import com.fixterminal.api.ports.RxStrategiesPort;
import com.fixterminal.shared.dictionaries.instruments.RxDicInstruments;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.strategies.business.parameters.RxTradeParametersDesk;
import com.fixterminal.strategies.business.trader.RxTradeControllersDesk;
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

        //TODO Monitorowanie tylko tych wskazanych które będą tradowane.
        //for (RxInstrument instrument : dicInstruments.getDefaultList())
        for (RxInstrument instrument : dicInstruments.toList())
        {
            tradeParametersDesk.initParameters(instrument);
        }

    }
}
