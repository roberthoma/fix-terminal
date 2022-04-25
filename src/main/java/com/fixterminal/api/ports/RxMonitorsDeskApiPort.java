package com.fixterminal.api.ports;


import com.fixterminal.shared.dictionaries.instruments.RxInstrument;

import java.util.List;

public interface RxMonitorsDeskApiPort {
    List<RxInstrument> getInstrumentsList();

    void startConnectionController();

    void startMonitors();
}
