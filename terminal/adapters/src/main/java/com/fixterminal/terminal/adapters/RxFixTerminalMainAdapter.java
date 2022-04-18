package com.fixterminal.terminal.adapters;

import com.fixterminal.terminal.business.application.RxFixTerminal;
import com.fixterminal.terminal.business.propmters.RxFixTerminalPrompter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RxFixTerminalMainAdapter implements RxFixTerminalMainPort {
     private static final Logger log = LoggerFactory.getLogger(RxFixTerminalMainAdapter.class);


    @Autowired
    RxFixTerminal terminal;


    @Autowired
    RxFixTerminalPrompter terminalPrompter;

    public  RxFixTerminalMainAdapter(){
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
    public Object getHeartBB() {
        return terminal.getHeartBB();
    }

    @Override
    public Object getFixEngineInfo() {
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