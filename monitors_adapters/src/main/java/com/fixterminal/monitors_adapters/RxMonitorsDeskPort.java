package com.fixterminal.monitors_adapters;


import com.fixterminal.shared.dictionaries.instruments.RxInstrument;

import java.util.List;

public interface RxMonitorsDeskPort {
    List<RxInstrument> getInstrumentsList();

    void startConnectionController();
}
