package com.fixterminal.app.ports;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.enumerators.RxActionType;

import java.util.List;

public interface RxMarketServicePort {

//    RxMarketMonitorStrategiesPort getMonitor(RxInstrument instrument);

    void start();

   void setAutoTradingOff();
   void setAutoTradingOn();

    RxActionType commandDecode(String cmd);


    List<RxInstrument> getInstrumentsList();

    void startConnectionController();

    void startMonitors();

}
