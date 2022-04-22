package com.fixterminal.terminal.adapters;

import com.fixterminal.gui.ports.RxFixTerminalMainGuiPort;
import com.fixterminal.terminal.business.application.RxFixTerminal;
import com.fixterminal.terminal.business.prompters.RxFixTerminalPrompter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RxFixTerminalMainGuiAdapter implements RxFixTerminalMainGuiPort {
     private static final Logger log = LoggerFactory.getLogger(RxFixTerminalMainGuiAdapter.class);


    @Autowired
    RxFixTerminal terminal;


    @Autowired
    RxFixTerminalPrompter terminalPrompter;

    public RxFixTerminalMainGuiAdapter(){
    log.info("Init : RxFixTerminalMainAdapter");
    }

    @Override
    public boolean isWorking() {
        return terminal.isWorking();
    }

    @Override
    public void start() throws IllegalAccessException, InstantiationException {
        terminal.start();
    }

    @Override
    public void logon(String user, String password) {
        terminal.logon(user, password);
    }

    @Override
    public void logout() {
        terminal.logout();
    }

    @Override
    public String getSessionSettings() {
        return terminal.getSessionSettings();
    }

    @Override
    public String getHeartBB() {
        return terminal.getHeartBB();
    }

    @Override
    public String getFixEngineInfo() {
        return terminal.getFixEngineInfo();
    }

    @Override
    public String getLog() {
        return terminalPrompter.getLog();
    }

    @Override
    public boolean isUserLogged(){
      return terminal.isLoggedOn();

    }

}