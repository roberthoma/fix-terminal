package com.fixterminal.app.terminal.adapters.gui;

import com.fixterminal.app.terminal.business.RxFixTerminal;
import com.fixterminal.app.terminal.business.services.RxFixTerminalPrompter;
//import com.fixterminal.app.gui.ports.RxFixTerminalMainPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RxFixTerminalMainAdapter implements RxFixTerminalMainPort {
//public class RxFixTerminalMainAdapter {

    @Autowired
    RxFixTerminal terminal;

    @Autowired
    RxFixTerminalPrompter terminalPrompter;

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
        terminal.logon(user,password);
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
}
