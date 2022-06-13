package com.fixterminal.market.business.sessions;

import com.fixterminal.market.business.monitors.RxMonitorsDesk;
import com.fixterminal.market.business.parameters.RxTradeParametersDesk;
import com.fixterminal.market.business.trade.actions.RxActionsManager;
import com.fixterminal.shared.dictionaries.instruments.RxDicInstruments;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.gui.ports.RxFixTerminalMainGuiPort;
import com.fixterminal.market.ports.RxMessageSenderPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//TODO  kontrola czy rynet jest dostępny  efekt wyłączzonego rynku

@Component
@Slf4j
public class RxSessionController extends Thread {

    RxFixTerminalMainGuiPort terminal;
    RxMessageSenderPort rxRequestMessageSender;

    RxMonitorsDesk monitorsDesk;

    RxDicInstruments dicInstruments;
    RxActionsManager actionsController;
    RxTradeParametersDesk parametersDesk;

    @Autowired
    public RxSessionController(RxFixTerminalMainGuiPort terminal,
                               RxMessageSenderPort rxRequestMessageSender,
                               RxMonitorsDesk monitorsDesk,
                               RxActionsManager actionsController,
                               RxTradeParametersDesk parametersDesk,
                               RxDicInstruments dicInstruments
    ){
        log.info("Init : RxConnectionControler");
        this.terminal = terminal;
        this.monitorsDesk = monitorsDesk;
        this.rxRequestMessageSender = rxRequestMessageSender;
        this.actionsController = actionsController;
        this.parametersDesk = parametersDesk;
        this.dicInstruments = dicInstruments;
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

        for (RxInstrument instrument : dicInstruments.toList())
           {
             if(parametersDesk.getTradeParameters(instrument).isMarketDataRequest()) {
                 rxRequestMessageSender.sendMarketDataRequest(instrument);
             }
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