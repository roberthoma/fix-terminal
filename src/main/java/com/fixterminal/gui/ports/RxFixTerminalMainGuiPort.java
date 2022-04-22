package com.fixterminal.gui.ports;

public interface RxFixTerminalMainGuiPort {
    boolean isWorking();

    void start() throws IllegalAccessException, InstantiationException;

    void logon(String user, String password);

    void logout();

    String getSessionSettings();

    String getHeartBB();

    String getFixEngineInfo();

    String getLog();

    boolean isUserLogged();

}
