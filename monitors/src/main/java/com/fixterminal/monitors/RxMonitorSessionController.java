package com.fixterminal.monitors;

import com.fixterminal.shared.dictionaries.instruments.RxDicInstruments;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.terminal.adapters.RxFixTerminalMainPort;
import com.fixterminal.terminal.adapters.RxRequestMessageSenderPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RxMonitorSessionController extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RxMonitorSessionController.class);

    RxFixTerminalMainPort terminal;
    RxRequestMessageSenderPort rxRequestMessageSender;

    @Autowired
    RxDicInstruments dicInstruments;

    @Autowired
    public RxMonitorSessionController(RxFixTerminalMainPort terminal,
                                      RxRequestMessageSenderPort rxRequestMessageSender
    ){
        log.info("Init : RxConnectionControler");
        this.terminal = terminal;
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

//        RxLogonService.initUserSession();

//        RxRequestMessageSender.getInstance().sendMarketDataRequest(RxDictionaries
//                .getInstance()
//                .getDefaultInstrument());

            for (RxInstrument instrument : dicInstruments.getDefaultList())
               {
                rxRequestMessageSender.sendMarketDataRequest(instrument);
            }


    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}


}