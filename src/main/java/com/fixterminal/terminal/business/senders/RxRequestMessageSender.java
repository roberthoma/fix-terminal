package com.fixterminal.terminal.business.senders;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.orders.RxOrderEntity;
import com.fixterminal.terminal.business.application.RxQuickFixTerminal;
import com.fixterminal.terminal.business.services.RxMessageDecorator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quickfix.Session;
import quickfix.field.*;

import java.util.UUID;



//TODO KONTROLER WYSYŁANYCH ZLECEŃ oczekiwanie na raport

@Component
@Slf4j
public class RxRequestMessageSender {

    RxQuickFixTerminal quickFixTerminal;

    @Autowired
    public RxRequestMessageSender(RxQuickFixTerminal quickFixTerminal){
        log.info("Init : "+this.getClass().getSimpleName());
        this.quickFixTerminal = quickFixTerminal;
    }





    public void sendMarketDataRequest(RxInstrument instrument){

        try
        {

            UUID uuid = UUID.randomUUID();
            String randomUUIDString = "12z1as";// TODO unikalny sktring
            quickfix.fix44.MarketDataRequest.NoMDEntryTypes group =
                    new quickfix.fix44.MarketDataRequest.NoMDEntryTypes();
            quickfix.fix44.MarketDataRequest.NoRelatedSym group1 =
                    new quickfix.fix44.MarketDataRequest.NoRelatedSym();

            //



            quickfix.fix44.MarketDataRequest message = new  quickfix.fix44.MarketDataRequest();
            message.setField(new MDReqID(randomUUIDString));
            message.setField(new SubscriptionRequestType(('1')));
            message.setField(new MarketDepth(0));  // Full Book = 0
            message.setField(new NoMDEntryTypes(1));

            //group.setField(new MDEntryType(MDEntryType.TRADE));
            //group.setField(new MDEntryType(MDEntryType.OFFER));
            group.setField(new MDEntryType(MDEntryType.BID));
            message.addGroup(group);
            group1.setField(new Symbol(instrument.getFixSymbol()));
            message.addGroup(group1);

            System.out.println("MARKET_DATA_REQUEST >"+
                           RxMessageDecorator.decorate( message));

            Session.sendToTarget(message, quickFixTerminal.getQuoteSessionsId());



        }catch (Exception e)
        {
           e.printStackTrace();
        }



    }
//----------------------------------
//Quote Request <R> message

    public void sendQuoteRequestMessage(RxInstrument instrument) {
        try {
            UUID uuid = UUID.randomUUID();
            quickfix.fix44.MarketDataRequest.NoMDEntryTypes group =
                    new quickfix.fix44.MarketDataRequest.NoMDEntryTypes();

            String randomUUIDString = "145z1avvv";//uuid.toString();

            quickfix.fix44.QuoteRequest message = new quickfix.fix44.QuoteRequest();
            message.setField(new QuoteReqID(randomUUIDString));

            message.setField(new NoRelatedSym(1));
            group.setField(new Symbol(instrument.getFixSymbol()));
            //group1.setField(new Symbol(symbolTextField.getText()));
            message.addGroup(group);



            System.out.println("end sendQuoteRequestMessage > ");
        } catch (Exception ex) {
            System.out.println("error" + ex);
        }



    }
    //FIX 4.4 : Order Status Request <H> message
    public void sendOrderStatusRequest(){
        try {
            quickfix.fix44.OrderStatusRequest  message = new quickfix.fix44.OrderStatusRequest();

            String randomUUIDString = "ord12z1axs";// TODO unikalny sktring


            message.getHeader().setField(new PosReqID(randomUUIDString));
            // message.getHeader().setField(new PosReqType(PosReqType.POSITIONS));
            // message.setField(new TotalNumPosReports(3));

            System.out.println("REQUEST_ORDER_STATUS>"+RxMessageDecorator.decorate( message));
         Session.sendToTarget(message,
                 quickFixTerminal.getTradeSessionsId());
        }
        catch (Exception e ){
            e.printStackTrace();
        }



    }


    public void sendNetworkStatusRequest(){
        try
        {    //Consumer<RxPendingOrdersMap> setPendingOrdersMapConsumer;


            quickfix.fix44.NetworkStatusRequest  message = new  quickfix.fix44.NetworkStatusRequest();
            UUID uuid = UUID.randomUUID();
            String randomUUIDString = "1332z1as";// TODO unikalny sktring

            message.getHeader().setField(new PosReqID(randomUUIDString));
            // message.getHeader().setField(new PosReqType(PosReqType.POSITIONS));

            System.out.println("NETWORK_STATUS_REQUEST>"+RxMessageDecorator.decorate( message));
            Session.sendToTarget(message, quickFixTerminal.getTradeSessionsId());

            //PartySubIDType.FIELD


        }catch (Exception ex)
        {
            ex.printStackTrace();
        }



    }


    public void sendTradingSessionStatusRequest(){
        try
        {

            quickfix.fix44.TradingSessionStatusRequest  message = new  quickfix.fix44.TradingSessionStatusRequest();
            UUID uuid = UUID.randomUUID();
            String randomUUIDString = "133as";// TODO unikalny sktring

            message.getHeader().setField(new PosReqID(randomUUIDString));
            // message.getHeader().setField(new PosReqType(PosReqType.POSITIONS));

            System.out.println("TRADING_SESSION_STATUS_REQUEST>"+ RxMessageDecorator.decorate( message));
            Session.sendToTarget(message, quickFixTerminal.getTradeSessionsId());

            //PartySubIDType.FIELD


        }catch (Exception ex)
        {
            ex.printStackTrace();
        }



    }

    public void sendOrderMassStatusRequest(){
        try
        {

            quickfix.fix44.OrderMassStatusRequest  message = new  quickfix.fix44.OrderMassStatusRequest();
            UUID uuid = UUID.randomUUID();
            String randomUUIDString = "133as";// TODO unikalny sktring

            message.getHeader().setField(new MassStatusReqID (randomUUIDString));

            message.getHeader().setField(new MassStatusReqType(MassStatusReqType.STATUS_FOR_ALL_ORDERS));

            // message.getHeader().setField(new PosReqType(PosReqType.POSITIONS));

            System.out.println("ORDER_MASS_STATUS_REQUEST>"+RxMessageDecorator.decorate( message));
            Session.sendToTarget(message, quickFixTerminal.getTradeSessionsId());

            //PartySubIDType.FIELD


        }catch (Exception ex)
        {
            ex.printStackTrace();
        }



    }




    //AN = Request For Positions <AN>

    public void sendRequestForPositions(){
        try
        {
           // todo change for debug System.out.println("BEFORE SEND sendRequestForPositions");
            //public static final String MSGTYPE = "AN";

            quickfix.fix44.RequestForPositions  message = new  quickfix.fix44.RequestForPositions();

            String randomUUIDString = "pos12zssX";// TODO unikalny sktring

            message.getHeader().setField(new PosReqID(randomUUIDString));
           // message.getHeader().setField(new PosReqType(PosReqType.POSITIONS));

            System.out.println("REQUEST_FOR_POSITIONS>"+
                    RxMessageDecorator.decorate( message))
                    ;
            Session.sendToTarget(message, quickFixTerminal.getTradeSessionsId());


        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }


    public void  sendOrderCancelRequest(String uniqueID){
       try{

           quickfix.fix44.OrderCancelRequest message = new quickfix.fix44.OrderCancelRequest();

        String randomUUIDString = "can321as";// TODO unique string

           ClOrdID clOrdID = new ClOrdID(randomUUIDString);
           message.set(clOrdID);

           OrigClOrdID origClOrdID = new OrigClOrdID(uniqueID);
           message.set(origClOrdID);

           System.out.println("ORDER_CANCEL_REQUEST>"+
                   RxMessageDecorator.decorate( message));

           System.out.println(RxMessageDecorator.decorate(message));
        Session.sendToTarget(message, quickFixTerminal.getTradeSessionsId());
      }
    catch (Exception ex)
    {
        ex.printStackTrace();
    }

    }


    public void  sendOrderReplaceRequest(RxOrderEntity order){
        try{

            quickfix.fix44.OrderCancelReplaceRequest message = new quickfix.fix44.OrderCancelReplaceRequest();

            String randomUUIDString = "rep321as";// TODO unique string

            ClOrdID clOrdID = new ClOrdID(randomUUIDString); //"381298068"); //randomUUIDString);
            message.set(clOrdID);

            OrigClOrdID origClOrdID = new OrigClOrdID(order.getClOrdID());
            message.set(origClOrdID);

//TODO do poprawki dla zleceń typu TakeProfit
// Price price = new Price();
//            price.setValue( pendingOrder.getPrice());
//            message.set(price);

            // tylko dla stop loss
            StopPx  price = new StopPx();
            price.setValue( order.getPrice());
            message.set(price);




            OrderQty  orderQty = new OrderQty();
            orderQty.setValue(order.getQuantity());
            message.set(orderQty);

            System.out.println("ORDER_CANCEL_REPLACE_REQUEST>"+
                    RxMessageDecorator.decorate( message));

            System.out.println(RxMessageDecorator.decorate(message));
            Session.sendToTarget(message, quickFixTerminal.getTradeSessionsId());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }




}
