package com.fixterminal.strategies.adapters;

import com.fixterminal.api.ports.RxStrategiesPort;
import com.fixterminal.strategies.business.trader.RxTradeControllersDesk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RxStrategiesAdapter implements RxStrategiesPort {

    RxTradeControllersDesk tradeControllersDesk;

    @Autowired
    public RxStrategiesAdapter(RxTradeControllersDesk tradeControllersDesk){
       this.tradeControllersDesk = tradeControllersDesk;
    }

    @Override
    public void start() {
        tradeControllersDesk.start();
    }
}
