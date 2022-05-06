package com.fixterminal.market.business.trader.controllers;

import com.fixterminal.market.business.monitors.RxMonitorsDesk;
import com.fixterminal.market.business.trader.actions.RxTradeActions;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class RxTradeControllersDesk {


    RxTradeActions tradeActions;

    RxMonitorsDesk  rxMonitorsDesk;
    private final Map<RxInstrument, RxTradeController> tradeControllersMap;

    @Autowired
    public RxTradeControllersDesk(RxMonitorsDesk  rxMonitorsDesk,
                                  RxTradeActions tradeActions){
      log.info("Init : " + this.getClass().getSimpleName());
      this.rxMonitorsDesk = rxMonitorsDesk;
      this.tradeActions = tradeActions;
      tradeControllersMap = new HashMap<>();
    }

    public void start() {

        rxMonitorsDesk.getMonitorsList().forEach(rxMonitor -> {
                    RxTradeController tradeController =  new RxTradeController();
                    tradeController.setTradeActions(tradeActions);
                    tradeController.setMonitor(rxMonitor);
                    tradeController.start();
                    tradeControllersMap.put(rxMonitor.getInstrument(), tradeController);
                }
        );
    }

}
