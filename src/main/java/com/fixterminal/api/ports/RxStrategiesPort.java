package com.fixterminal.api.ports;

import com.fixterminal.commands.base.RxCommandsEnum;

public interface RxStrategiesPort {

   void start();

   void setAutoTradingOff();

    RxCommandsEnum commandDecode(String cmd);

}
