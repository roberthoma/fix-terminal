package com.fixterminal.terminal.domain.application;

import com.fixterminal.terminal.domain.prompters.RxFixTerminalPrompter;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quickfix.*;
import quickfix.field.MsgType;
import quickfix.field.Password;
import quickfix.field.Username;

@Component
public class RxQuickFixApplication implements Application {
    private static final Logger log = LoggerFactory.getLogger(RxQuickFixApplication.class);


    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;


    @Autowired
    RxQuickFixMessageDispatcher messageDispatcher;


   @Autowired
   RxFixTerminalPrompter printInfoConsumer;


   public RxQuickFixApplication(){
      log.info("Init : RxQuickFixApplication");
//      printInfoConsumer.accept("Init : RxQuickFixApplication");
   }

    @Override
    public void onCreate(SessionID sessionId) {
        printInfoConsumer.accept(" !!! NOT IMPLEMENTED !!! RxQuickFixApplication.onCreate sessionId:" + sessionId);
    }

    @Override
    public void onLogon(SessionID sessionId) {
        if (sessionId.getTargetSubID().compareTo("TRADE")==0){
            printInfoConsumer.accept("> Setting default TRADE session : "+sessionId);
        }
        if (sessionId.getTargetSubID().compareTo("QUOTE")==0){
            printInfoConsumer.accept("> Setting default QUOTE session : "+sessionId);
        }
    }

    @Override
    public void onLogout(SessionID sessionId) {
        printInfoConsumer.accept("> Logout SESSION_ID : "+sessionId);
    }

    @Override
    public void toAdmin(Message message, SessionID sessionId) {
        try {
            final String msgType = message.getHeader().getString(MsgType.FIELD);
            if (MsgType.LOGON.compareTo(msgType) == 0) {

                message.setField(new StringField(Username.FIELD, username));;
                message.setField(new StringField(Password.FIELD, password));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        messageDispatcher.dispose(message,sessionId);
    }

    @Override
    public void toApp(Message message, SessionID sessionId) throws DoNotSend {
        printInfoConsumer.accept(" !!! NOT IMPLEMENTED !!! RxQuickFixApplication.toApp message:" + message.toString());
    }

    @Override
    public void fromApp(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        messageDispatcher.dispose(message,sessionId);
    }

}
