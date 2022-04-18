package com.fixterminal.terminal.business.application;



import com.fixterminal.terminal.business.propmters.RxFixTerminalPrompter;
import lombok.Getter;
import lombok.Setter;
import org.quickfixj.jmx.JmxExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quickfix.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class RxQuickFixTerminal  {
    private static final Logger log = LoggerFactory.getLogger(RxQuickFixTerminal.class);

   @Autowired
    private RxQuickFixApplication quickFixApp;

    private MessageStoreFactory messageStoreFactory;
    private LogFactory logFactory;
    private SessionSettings settings;
    private MessageFactory messageFactory;
    private JmxExporter exporter;
    private Initiator initiator ;


//    @Getter
//    @Setter
//    SessionID tradeSessionsId;
//
//    @Getter
//    @Setter
//    SessionID quoteSessionsId;


    private boolean initiatorStarted = false;
    @Getter

    @Autowired
    RxFixTerminalPrompter printInfoConsumer;


    public RxQuickFixTerminal(){
        log.info("Init : RxQuickFixTerminal");
    }


    public String getSessionSettings(){
        if (settings != null) {
            return settings.toString();
        }
        return "Terminal not started";
    }


    private void readSessionSettings() throws ConfigError, IOException {
        log.info("ReadSessionSettings");
        //File initialFile = new File(cfgPath+"/"+configFileName);F

        File initialFile = new File("app-config/fix_setting.cfg");
        log.info("path:"+ initialFile.getAbsoluteFile().getPath());
        InputStream inputStream = new FileInputStream(initialFile);
        if (inputStream == null) {
            return;
        }
        settings = new SessionSettings(inputStream);
        inputStream.close();
    }

    public void start() throws InstantiationException, IllegalAccessException {
        try {

            printInfoConsumer.accept(" > Reading Sessions configuration");
            readSessionSettings();

//            printInfoConsumer.accept(" > Starting > QuickFix > Application - RxQuickFixApplication ");
//            quickFixApp = new RxQuickFixApplication(printInfoConsumer);

            printInfoConsumer.accept(" > Starting > QuickFix > FileStoreFactory");
            messageStoreFactory = new FileStoreFactory(settings);

            printInfoConsumer.accept(" > Starting > QuickFix > ScreenLogFactory");

            //TODO parametr czy debuging
            //            if (RxTickToolCmdLineApp.isModeDebug) {
//          // logFactory = new ScreenLogFactory(false, false, true, false);
            // logFactory = new ScreenLogFactory(false, true, true, true);
             logFactory = new ScreenLogFactory(true, true, true, false);
         //     logFactory = new ScreenLogFactory(false, false, false, true);
            // logFactory = new ScreenLogFactory(false, false, false, true);
            //                    }
            //                  else {
            //               logFactory = new ScreenLogFactory(false, false, false, false);
            //                }


           FileLogFactory fileLogFactory = new FileLogFactory(settings);


            printInfoConsumer.accept(" > Starting > QuickFix > DefaultMessageFactory");
            messageFactory = new DefaultMessageFactory();

            printInfoConsumer.accept(" > Starting > QuickFix > SocketInitiator");
            initiator = new SocketInitiator(quickFixApp, messageStoreFactory, settings, logFactory, messageFactory);


            printInfoConsumer.accept(" > Starting > QuickFix > JmxExporter");
            exporter = new JmxExporter();

            printInfoConsumer.accept(" > Starting > QuickFix > Registering initiator");
            exporter.register(initiator);

            printInfoConsumer.accept(" > Starting > Message dispatcher");
        }
        catch (Exception e){
            printInfoConsumer.accept(e.toString());
            e.printStackTrace();
        }


    }

    public synchronized void logon(String username, String password) {
        quickFixApp.setUsername(username);
        quickFixApp.setPassword(password);
        if (!initiatorStarted) {
            try {

                initiator.start();
                printInfoConsumer.accept("> Initiator ["+username+"] started.");
                initiatorStarted = true;
            //    isUserLogon      = true;

            } catch (Exception e) {
                log.error("Logon failed", e);
            }
        } else {
            for (SessionID sessionId : initiator.getSessions()) {
                printInfoConsumer.accept("> Session  ["+sessionId.toString()+"] on.");
                Session.lookupSession(sessionId).logon();
            }
    //        isUserLogon = true;
        }
    }

    public void logout() {
        for (SessionID sessionId : initiator.getSessions()) {
            Session.lookupSession(sessionId).logout("user requested");
            printInfoConsumer.accept("> QuickFix : user requested : logout");
        }

        initiator.stop();
    }

    public List<SessionID> getSessions(){
        return initiator.getSessions();
    }

    public boolean isLoggedOn(){
      return initiator.isLoggedOn();
    }

    private quickfix.SessionID getSessionIdByTargetSubID(String sessionSubId){
        for (quickfix.SessionID sessionId : initiator.getSessions()
        )
        {
            if (sessionId.getTargetSubID().compareTo(sessionSubId )==0){
                return sessionId;
            }
        }
        return null;


    }

    public  quickfix.SessionID getQuoteSessionsId() {
        return  getSessionIdByTargetSubID("QUOTE");
    }

    public quickfix.SessionID getTradeSessionsId() {
        return  getSessionIdByTargetSubID("TRADE");
    }




}
