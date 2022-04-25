package com.fixterminal.terminal.business.application;

import com.fixterminal.terminal.business.prompters.RxFixHeartPrompter;
import com.fixterminal.terminal.business.prompters.RxFixTerminalPrompter;
import org.quickfixj.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RxFixTerminal   {
    private static final Logger log = LoggerFactory.getLogger(RxFixTerminal.class);

    @Autowired
    private RxQuickFixTerminal rxQuickFixTerminal;

    @Autowired
    RxFixTerminalPrompter printInfoConsumer;

    @Autowired
    RxFixHeartPrompter heartPrompter ;

    //TODO tryby pracy server
    boolean isWork = false;


    public RxFixTerminal() {
        super();
        log.info("Init : RxFixTerminal");
    }




//    public void setConfigPath(String cfgPath) {
//        rxQuickFixTerminal.setConfigPath(cfgPath);
//    }
//
//    public void setEngineConfigFileName(String configFileName) {
//        rxQuickFixTerminal.setConfigFileName(configFileName);
//    }

    public void start() throws IllegalAccessException, InstantiationException {
        printInfoConsumer.accept("[RxTerminal starting]");
        rxQuickFixTerminal.start();
        printInfoConsumer.accept("[RxTerminal started] \n");
        isWork = true; //TODO Tymczasowe tylko na potrzenby kontroli

    }

    public String getSessionSettings() {
        return rxQuickFixTerminal.getSessionSettings();
    }

    public void logout(){
        printInfoConsumer.accept("[RxTerminal logout]");
        rxQuickFixTerminal.logout();
        printInfoConsumer.accept("[RxTerminal logged out]");
    }

    public void logon(String user , String password){
        printInfoConsumer.accept("[RxTerminal login]");
        rxQuickFixTerminal.logon(user,password);
        printInfoConsumer.accept("[RxTerminal logged]");

    }

    public boolean isWorking() {
      return isWork;
    }

    public String getFixEngineInfo(){
       return "Quickfixj : " + Version.getVersion();
    }

    public String getHeartBB(){
        return heartPrompter.getLog();
    }




public boolean isLoggedOn(){
    return  rxQuickFixTerminal.isLoggedOn();
}

    //TODO ta funkcja nie jest wystarczająca
//    public boolean isUserLogon() {
//        RxQuickFixTerminal quickFixTerminal  = RxQuickFixTerminal.getInstance();
//
//     return quickFixTerminal.getQuoteSessionsId() != null
//         && quickFixTerminal.getTradeSessionsId() != null
//         && quickFixTerminal.isUserLogon();
//    }

}
