package com.fixterminal.terminal.business.application;


import com.fixterminal.terminal.business.prompters.RxFixHeartPrompter;
import com.fixterminal.terminal.business.prompters.RxFixTerminalPrompter;
import com.fixterminal.shared.market.RxExecuteReport;
import com.fixterminal.shared.market.RxMarketDataVO;
import com.fixterminal.shared.positions.RxPosition;
import com.fixterminal.terminal.business.senders.RxOrderController;
import com.fixterminal.terminal.business.services.RxMarketDataService;
import com.fixterminal.terminal.business.services.RxMassageToExecutionReport;
import com.fixterminal.terminal.business.services.RxMessageDecorator;
import com.fixterminal.terminal.business.services.RxMessageToPositionService;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quickfix.Message;
import quickfix.SessionID;
import quickfix.field.MsgType;
import quickfix.field.SenderSubID;
import quickfix.field.TargetCompID;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Component
public class RxQuickFixMessageDispatcher {
    private static final Logger log = LoggerFactory.getLogger(RxQuickFixMessageDispatcher.class);

    Runnable heartPulseRunner;

    @Autowired
    RxFixTerminalPrompter logPrompter;

    @Autowired
    RxMarketDataService rxMarketDataService;

    @Autowired
    RxMessageToPositionService messageToPositionService;

    @Autowired
    RxMassageToExecutionReport massageToExecutionReport;

    @Autowired
    RxFixHeartPrompter heartPrompter;

    @Autowired
    RxOrderController orderController;

    public RxQuickFixMessageDispatcher() {
        log.info("Init : RxQuickFixMessageDispatcher");
    }

    //TMP
    int hertBB = 0;

    @Setter
    Consumer<RxPosition> positionReportConsumer;

    @Setter
    Consumer<RxExecuteReport> executionReportConsumer;

    @Setter
    BiConsumer<Boolean, List<RxMarketDataVO>> marketDataConsumer;



    public void setHeartPulseRunner(Runnable r) {
        heartPulseRunner = r;
    }


    public void dispose(Message message, SessionID sessionId) {
        try {
            final String msgType = message.getHeader().getString(MsgType.FIELD);

            if(msgType.compareTo(MsgType.HEARTBEAT)==0
            || msgType.compareTo(MsgType.EXECUTION_REPORT)==0
            || msgType.compareTo(MsgType.POSITION_REPORT)==0
            ){
                    if (heartPulseRunner != null) {   //TODO uruchmić dla modulo %2  heart biting przychodza 2 pod rząd
                        heartPulseRunner.run();
                    }
            }


            switch (msgType){
                case MsgType.HEARTBEAT :
                    hertBB ++;
                    //heartPrompter.accept("HEARTBEAT> "+hertBB);
                    System.out.print(".");
                    break;


                case MsgType.MARKET_DATA_SNAPSHOT_FULL_REFRESH:
                   // logPrompter.accept("MARKET_DATA_SNAPSHOT_FULL_REFRESH>"+ RxMessageDecorator.decorate(message));
                    if (marketDataConsumer != null) {
                        marketDataConsumer.accept(true, rxMarketDataService.messageToList(message, null));
                    }
                    break;

                case MsgType.MARKET_DATA_INCREMENTAL_REFRESH:
                  //  logPrompter.accept("MARKET_DATA_INCREMENTAL_REFRESH>"+RxMessageDecorator.decorate(message));
                    if (marketDataConsumer != null) {
                        marketDataConsumer.accept(false, rxMarketDataService.messageToList(message, null));
                    }
                    break;

                case MsgType.LOGON:
                    //TODO MOVE to decorate function
                    System.out.println(" LOGON "+ message.getHeader().getString(SenderSubID.FIELD)
                            + " "+message.getHeader().getString(TargetCompID.FIELD));

//                    RxFixTerminal.getInstance().sendOrderMassStatusRequest(); //TODO Tymczasowe

                    break;

                case MsgType.LOGOUT:
                     System.out.println(" LOGOUT "+ message.getHeader().getString(SenderSubID.FIELD));
                    //           + " "+message.getHeader().getString(TargetCompID.FIELD));
                    //System.out.println("LOGON >> "+message);
                    break;

                case MsgType.EXECUTION_REPORT:
                    //logPrompter.accept("EXECUTION_REPORT>"+RxMessageDecorator.decorate(message));
                    System.out.println("EXECUTION_REPORT>"+RxMessageDecorator.decorate(message));
                    RxExecuteReport report =  massageToExecutionReport.toExecuteReport(message);
                    orderController.setStatus(report);
                      if(executionReportConsumer != null) {
                          executionReportConsumer.accept(report);
                      }
                    break;

                case MsgType.POSITION_REPORT:
//
                    //logPrompter.accept(("POSITION_REPORT>"+RxMessageDecorator.decorate(message)));
                    System.out.println(("POSITION_REPORT>"+RxMessageDecorator.decorate(message)));

                    if(  positionReportConsumer != null){
                        positionReportConsumer.accept(messageToPositionService.toPosition(message));

                    }
//

                    break;


                default :
//                    System.out.println(">> NOT IMPL MSG >>"+RxMessageDecorator.decorate( message));


            }


        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
}

