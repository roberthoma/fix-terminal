package com.fixterminal.monitors_adapters;

import com.fixterminal.monitors.RxMonitorsDesk;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RxMonitorsDeskAdapter implements RxMonitorsDeskPort {
    private static final Logger log = LoggerFactory.getLogger(RxMonitorsDeskAdapter.class);

    @Autowired
    RxMonitorsDesk rxMonitorsDesk;

    public RxMonitorsDeskAdapter() {
        log.info("Init : RxMonitorsDeskAdapter ");
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
}
