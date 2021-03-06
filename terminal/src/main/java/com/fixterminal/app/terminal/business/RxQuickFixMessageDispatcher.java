package com.fixterminal.app.terminal.business;


import com.fixterminal.app.terminal.business.services.RxFixHeartPrompter;
import com.fixterminal.app.terminal.business.services.RxFixTerminalPrompter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quickfix.Message;
import quickfix.SessionID;
import quickfix.field.MsgType;
import quickfix.field.SenderSubID;
import quickfix.field.TargetCompID;

@Component
public class RxQuickFixMessageDispatcher {
    private static final Logger log = LoggerFactory.getLogger(RxQuickFixMessageDispatcher.class);

    Runnable heartPulseRunner;

    @Autowired
    RxFixTerminalPrompter logPrompter;

    @Autowired
    RxFixHeartPrompter heartPrompter;

    public RxQuickFixMessageDispatcher() {
        log.info("Init : RxQuickFixMessageDispatcherImpl");
    }

    //TMP
    int hertBB = 0;

//    @Setter
//    Consumer<RxPosition> positionReportConsumer;
//
//    @Setter
//    Consumer<RxExecuteReport> executionReportConsumer;
//
//    @Setter
//    BiConsumer<Boolean,List<RxMarketDataVO>> marketDataConsumer;



    public void setHeartPulseRunner(Runnable r) {
        heartPulseRunner = r;
    }

//    public void setPositionReportConsumer (Consumer<RxPosition> consumer){
//
//        positionReportConsumer = consumer;
//
//    }

//    public void setMarketDataConsumer(BiConsumer<Boolean, List<RxMarketDataVO>> marketDataConsumer){
//        this.marketDataConsumer = marketDataConsumer;
//    }

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
                    heartPrompter.accept("HEARTBEAT> "+hertBB);
                //    System.out.println("HEARTBEAT>"+RxMessageDecorator.decorate(message));
                    break;


                case MsgType.MARKET_DATA_SNAPSHOT_FULL_REFRESH:
             //       System.out.println("MARKET_DATA_SNAPSHOT_FULL_REFRESH>"+RxMessageDecorator.decorate(message));
//                    if (marketDataConsumer != null) {
//                        marketDataConsumer.accept(true, RxMarketDataService.messageToList(message, null));
//                    }
                    break;

                case MsgType.MARKET_DATA_INCREMENTAL_REFRESH:
              //      System.out.println("MARKET_DATA_INCREMENTAL_REFRESH>"+RxMessageDecorator.decorate(message));
//                    if (marketDataConsumer != null) {
//                        marketDataConsumer.accept(false, RxMarketDataService.messageToList(message, null));
//                    }
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
//
//                     System.out.println("EXECUTION_REPORT>"+RxMessageDecorator.decorate(message));
//                      if(executionReportConsumer != null) {
//                          executionReportConsumer.accept(
//                                  RxMassegeToExecutionReport.toExecuteReport(message)
//                          );
//                      }
//                      RxRequestMessageSender.getInstance().sendRequestForPositions();
                    break;

                case MsgType.POSITION_REPORT:
//
//                    System.out.println("POSITION_REPORT>"+RxMessageDecorator.decorate(message));
//
//                    if(  positionReportConsumer != null){
//                        positionReportConsumer.accept(RxMessageToPositionService.toPosition(message));
//
//                    }
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

