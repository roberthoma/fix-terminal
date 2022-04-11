package com.fixterminal.app.monitors.adapters;

import com.fixterminal.app.monitors.business.RxMonitorsDesk;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RxMonitorsDeskAdapter implements RxMonitorsDeskPort{

    @Autowired
    RxMonitorsDesk monitorsDesk;

    @Override
    public List<RxInstrument> getInstrumentsList() {
    //    monitorsDesk.getMonitorsMap().
        return null;
    }
}
