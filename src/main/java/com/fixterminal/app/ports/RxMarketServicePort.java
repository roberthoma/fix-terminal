package com.fixterminal.app.ports;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.enumerators.RxAction;

import java.util.List;

public interface RxMarketServicePort {

//    RxMarketMonitorStrategiesPort getMonitor(RxInstrument instrument);

    void start();

   void setAutoTradingOff();
   void setAutoTradingOn();

    RxAction commandDecode(String cmd);


    List<RxInstrument> getInstrumentsList();

    void startConnectionController();

    void startMonitors();

}
