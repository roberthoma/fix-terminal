package com.fixterminal.market.business.monitors;

import com.fixterminal.shared.dictionaries.instruments.RxDicInstruments;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.gui.ports.RxFixTerminalMainGuiPort;
import com.fixterminal.market.ports.RxRequestMessageSenderPort;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//TODO  kontrola czy rynet jest dostępny  efekt wyłączzonego rynku

@Component
@Slf4j
public class RxMonitorSessionController extends Thread {

    RxFixTerminalMainGuiPort terminal;
    RxRequestMessageSenderPort rxRequestMessageSender;

    RxMonitorsDesk monitorsDesk;
    @Autowired
    RxDicInstruments dicInstruments;

    @Autowired
    public RxMonitorSessionController(RxFixTerminalMainGuiPort terminal,
                                      RxRequestMessageSenderPort rxRequestMessageSender,
                                      RxMonitorsDesk monitorsDesk
    ){
        log.info("Init : RxConnectionControler");
        this.terminal = terminal;
        this.monitorsDesk = monitorsDesk;
        this.rxRequestMessageSender = rxRequestMessageSender;
    }


    public void run()  {
    boolean _exit=false;
    int testInc = 0;

        try {
        log.info(" Waiting for user login... ");
        do {
            testInc++;
            if(testInc > 100
                    || terminal.isUserLogged()
            ){
                _exit = true;



            }
            System.out.print("X"); //TODO Wiatraczek :-) /-\ | ...
            Thread.sleep(10L * 100L);
        } while (!_exit);

        if(terminal.isUserLogged()){
          log.info("SESSION_CTRL : USER LOGGED");
        }

        //TODO Monitorowanie tylko tych wskazanych które będą tradowane.
        //for (RxInstrument instrument : dicInstruments.getDefaultList())
        for (RxInstrument instrument : dicInstruments.toList())
           {
            rxRequestMessageSender.sendMarketDataRequest(instrument);
            monitorsDesk.initMonitor(instrument);
        }

       // TODO For developing loop controlled connections etc

    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}


}