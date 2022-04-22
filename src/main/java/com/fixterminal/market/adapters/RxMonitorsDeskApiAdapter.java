package com.fixterminal.market.adapters;

import com.fixterminal.api.ports.RxMonitorsDeskApiPort;
import com.fixterminal.market.business.monitors.RxMonitorSessionController;
import com.fixterminal.market.business.monitors.RxMonitorsDesk;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RxMonitorsDeskApiAdapter implements RxMonitorsDeskApiPort {
    private static final Logger log = LoggerFactory.getLogger(RxMonitorsDeskApiAdapter.class);

    RxMonitorsDesk rxMonitorsDesk;
    RxMonitorSessionController connectionController;
    @Autowired
    public RxMonitorsDeskApiAdapter(RxMonitorsDesk rxMonitorsDesk,
                                    RxMonitorSessionController connectionController
    ) {
        log.info("Init : RxMonitorsDeskAdapter ");
        this.rxMonitorsDesk = rxMonitorsDesk;
        this.connectionController = connectionController;
    }
    @Override
    public List<RxInstrument> getInstrumentsList() {
       List<RxInstrument> instruments = new ArrayList<>();
       if ( rxMonitorsDesk !=null) {
           rxMonitorsDesk.getMonitorsMap().forEach((rxInstrument, rxMonitorThread) -> instruments.add(rxInstrument));
           return instruments;
       }
       else {
           System.out.println("ROHO monitorsDesk  JEST NULOWY");
       }
       return null;

    }

    @Override
    public void startConnectionController(){
        connectionController.start();
    }



}
