package com.fixterminal.market.business.trade.actions;

import com.fixterminal.market.business.monitors.RxMonitorsDesk;
import com.fixterminal.market.business.parameters.RxTradeParametersDesk;
import com.fixterminal.market.business.trade.actions.RxActionsController;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class RxActionsControllersDesk {
    @Autowired
    private ApplicationContext context;
    RxMonitorsDesk  rxMonitorsDesk;

    RxTradeParametersDesk parametersDesk;
    private final Map<RxInstrument, RxActionsController> actionsControllersMap;

    @Autowired
    public RxActionsControllersDesk(RxMonitorsDesk  rxMonitorsDesk,
                                    RxTradeParametersDesk parametersDesk){
      log.info("Init : " + this.getClass().getSimpleName());
      this.rxMonitorsDesk = rxMonitorsDesk;
      this.parametersDesk = parametersDesk;
      actionsControllersMap = new HashMap<>();
    }

    public void start() {
        rxMonitorsDesk.getMonitorsList().forEach(rxMonitor -> {
                    RxActionsController actionsController =  context.getBean(RxActionsController.class);
                    actionsController.setMonitor(rxMonitor);
                    actionsController.setParameters(parametersDesk.getTradeParameters(rxMonitor.getInstrument()));
                    actionsController.start();
                    actionsControllersMap.put(rxMonitor.getInstrument(), actionsController);
                }
        );
    }

}
