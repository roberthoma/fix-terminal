package com.fixterminal.app.terminal.app;

public interface RxFixTerminalMainPort {
    boolean isWorking();

    void start() throws IllegalAccessException, InstantiationException;

    void logon(String user, String password);

    void logout();

    String getSessionSettings();

    Object getHeartBB();

    Object getFixEngineInfo();

    String getLog();
}
