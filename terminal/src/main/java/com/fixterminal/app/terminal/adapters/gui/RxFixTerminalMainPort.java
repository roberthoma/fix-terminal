package com.fixterminal.app.terminal.adapters.gui;

public interface RxFixTerminalMainPort {
    boolean isWorking();

    void start() throws IllegalAccessException, InstantiationException;

    void logon(String user, String password);

    void logout();

    String getSessionSettings();

    String getHeartBB();

    String getFixEngineInfo();

    String getLog();

}
