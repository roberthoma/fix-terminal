package com.fixterminal.market.adapters;

import com.fixterminal.market.business.monitors.RxMonitorSessionController;
import com.fixterminal.market.business.monitors.RxMonitorsDesk;
import com.fixterminal.shared.enumerators.RxAction;
import com.fixterminal.app.ports.RxMarketServicePort;
import com.fixterminal.shared.dictionaries.instruments.RxDicInstruments;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.market.business.parameters.RxTradeParametersDesk;
import com.fixterminal.market.business.trader.controllers.RxTradeControllersDesk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RxMarketServiceAdapter implements RxMarketServicePort {

    RxMonitorsDesk rxMonitorsDesk;
    RxMonitorSessionController connectionController;
    RxTradeControllersDesk tradeControllersDesk;
    RxTradeParametersDesk tradeParametersDesk;
    RxDicInstruments dicInstruments;

    @Autowired
    public RxMarketServiceAdapter(RxDicInstruments dicInstruments,
                                  RxTradeControllersDesk tradeControllersDesk,
                                  RxTradeParametersDesk tradeParametersDesk,
                                  RxMonitorSessionController connectionController,
                                  RxMonitorsDesk rxMonitorsDesk
    ) {
        this.dicInstruments = dicInstruments;
        this.tradeControllersDesk = tradeControllersDesk;
        this.tradeParametersDesk = tradeParametersDesk;
        this.connectionController = connectionController;
        this.rxMonitorsDesk = rxMonitorsDesk;
    }


    @Override
    public void start() {
        tradeControllersDesk.start();

        //TODO Monitorowanie tylko tych wskazanych które będą monitorowane :)
        //for (RxInstrument instrument : dicInstruments.getMonitoredList())
        for (RxInstrument instrument : dicInstruments.toList()) {
            tradeParametersDesk.initParameters(instrument);
            //tradeParametersDesk.
        }


    }


    //TODO Type. imediately : close all open position stc
    @Override
    public void setAutoTradingOff() {
        tradeParametersDesk.setAutoTradingOff();
    }

    @Override
    public void setAutoTradingOn() {
        tradeParametersDesk.setAutoTradingOn();
    }

    @Override
    public RxAction commandDecode(String cmd) {
        //   ????

        return null;
    }

    public List<RxInstrument> getInstrumentsList() {
        if (rxMonitorsDesk != null) {
            return rxMonitorsDesk.getMonitoredInstrumentList();
        } else {
            System.out.println("ROHO monitorsDesk  JEST NULOWY");
        }
        return null;

    }

    @Override
    public void startConnectionController() {
        connectionController.start();
    }

    @Override
    public void startMonitors() {
        for (RxInstrument instrument : dicInstruments.toMonitoringList()) {
            rxMonitorsDesk.initMonitor(instrument);
        }

    }

}