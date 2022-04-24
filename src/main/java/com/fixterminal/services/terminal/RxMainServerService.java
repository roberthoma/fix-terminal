package com.fixterminal.services.terminal;

import com.fixterminal.api.ports.RxMonitorsDeskApiPort;
import com.fixterminal.api.ports.RxStrategiesPort;
import com.fixterminal.gui.ports.RxFixTerminalMainGuiPort;
import com.fixterminal.services.user.UserService;
import com.fixterminal.shared.dictionaries.RxDictionaries;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RxMainServerService {



    @Autowired
    private RxFixTerminalMainGuiPort terminal;

    private RxMonitorsDeskApiPort monitorsDesk;


    private RxStrategiesPort strategies;


    RxDictionaries dictionaries;

    UserService userService;

    @Autowired
    public RxMainServerService(UserService userService,
                               RxDictionaries dictionaries,
                               RxStrategiesPort strategies,
                               RxMonitorsDeskApiPort monitorsDesk,
                               RxFixTerminalMainGuiPort terminal ) {
      log.info("Init : RxMainServerService");

        this.userService =  userService;
        this.dictionaries = dictionaries;
        this.strategies = strategies;
        this.monitorsDesk = monitorsDesk;
        this.terminal = terminal;


    }


    public void serverStart() throws IllegalAccessException, InstantiationException {
        if (!terminal.isWorking()) {
            terminal.start();
            monitorsDesk.startConnectionController();

            //TODO Inicjacja parametrów tradigu ..
            strategies.start();

            //TODO obsługa błędów logowania
            userService.readFromFile();
            terminal.logon(userService.getUser(), userService.getPassword());

        }
    }

    public String getTerminalog() {
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

    public RxMonitorsDeskApiPort getMonitorsDesk() {
      return monitorsDesk;
    }
}
