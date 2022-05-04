package com.fixterminal.app.services;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.app.ports.RxMarketServicePort;
import com.fixterminal.gui.ports.RxFixTerminalMainGuiPort;
import com.fixterminal.shared.dictionaries.RxDictionaries;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class RxMainServerService {



    private final RxFixTerminalMainGuiPort terminal;

    private final RxMarketServicePort marketService;


    RxDictionaries dictionaries;

    RxUserService rxUserService;

    @Autowired
    public RxMainServerService(RxUserService rxUserService,
                               RxDictionaries dictionaries,
                               RxMarketServicePort marketService,
                               RxFixTerminalMainGuiPort terminal ) {
      log.info("Init : RxMainServerService");

        this.rxUserService = rxUserService;
        this.dictionaries = dictionaries;
        this.marketService = marketService;
        this.terminal = terminal;


    }


    public void serverStart() throws IllegalAccessException, InstantiationException, ParserConfigurationException, IOException, SAXException {
        if (!terminal.isWorking()) {
            terminal.start();

            marketService.startConnectionController();

            marketService.startMonitors();


            //TODO Inicjacja parametrów tradigu ..
            marketService.start();

            //TODO obsługa błędów logowania
            rxUserService.readFromFile();
            terminal.logon(rxUserService.getUser(), rxUserService.getPassword());

        }
    }

    public String getTerminalLog() {
       return terminal.getLog();
    }

    public void terminalLogout() {
        terminal.logout();
    }

    public RxFixTerminalMainGuiPort getTerminal(){
      return terminal;
    }

    public RxDictionaries getDictionaries(){
      return dictionaries;
    }

    public void setAutoTradingOff(){
        marketService.setAutoTradingOff();

    }

    public List<RxInstrument> getInstrumentsList() {
        return marketService.getInstrumentsList();
    }
}
