package com.fixterminal.app.monitors.adapters;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;

import java.util.List;

public interface RxMonitorsDeskPort {
    List<RxInstrument> getInstrumentsList();
}
