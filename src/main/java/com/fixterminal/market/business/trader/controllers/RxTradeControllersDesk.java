package com.fixterminal.market.business.trader.controllers;

import com.fixterminal.market.business.monitors.RxMonitorsDesk;
import com.fixterminal.market.business.parameters.RxTradeParametersDesk;
import com.fixterminal.market.business.trader.actions.RxTradeActions;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class RxTradeControllersDesk {
    @Autowired
    private ApplicationContext context;
    RxMonitorsDesk  rxMonitorsDesk;

    RxTradeParametersDesk parametersDesk;
    private final Map<RxInstrument, RxTradeController> tradeControllersMap;

    @Autowired
    public RxTradeControllersDesk(RxMonitorsDesk  rxMonitorsDesk,
                                  RxTradeParametersDesk parametersDesk){
      log.info("Init : " + this.getClass().getSimpleName());
      this.rxMonitorsDesk = rxMonitorsDesk;
      this.parametersDesk = parametersDesk;
      tradeControllersMap = new HashMap<>();
    }

    public void start() {
        rxMonitorsDesk.getMonitorsList().forEach(rxMonitor -> {
                    RxTradeController tradeController =  context.getBean(RxTradeController.class);
                    tradeController.setMonitor(rxMonitor);
                    tradeController.setParameters(parametersDesk.getTradeParameters(rxMonitor.getInstrument()));
                    tradeController.start();
                    tradeControllersMap.put(rxMonitor.getInstrument(), tradeController);
                }
        );
    }

}
