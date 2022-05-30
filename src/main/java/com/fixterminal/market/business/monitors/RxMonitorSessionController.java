package com.fixterminal.market.business.monitors;

import com.fixterminal.market.business.trade.actions.RxActionsManager;
import com.fixterminal.shared.dictionaries.instruments.RxDicInstruments;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.gui.ports.RxFixTerminalMainGuiPort;
import com.fixterminal.market.ports.RxMessageSenderPort;
import com.fixterminal.shared.enumerators.RxRequestStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//TODO  kontrola czy rynet jest dostępny  efekt wyłączzonego rynku

@Component
@Slf4j
public class RxMonitorSessionController extends Thread {

    RxFixTerminalMainGuiPort terminal;
    RxMessageSenderPort rxRequestMessageSender;

    RxMonitorsDesk monitorsDesk;
    @Autowired
    RxDicInstruments dicInstruments;
    RxActionsManager actionsController;

    @Autowired
    public RxMonitorSessionController(RxFixTerminalMainGuiPort terminal,
                                      RxMessageSenderPort rxRequestMessageSender,
                                      RxMonitorsDesk monitorsDesk,
                                      RxActionsManager actionsController
    ){
        log.info("Init : RxConnectionControler");
        this.terminal = terminal;
        this.monitorsDesk = monitorsDesk;
        this.rxRequestMessageSender = rxRequestMessageSender;
        this.actionsController = actionsController;
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
        }

        rxRequestMessageSender.sendRequestForPositions();
        rxRequestMessageSender.sendOrderStatusRequest();
        rxRequestMessageSender.sendOrderMassStatusRequest();

       // TODO For developing loop controlled connections etc

    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}


}