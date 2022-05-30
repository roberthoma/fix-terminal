package com.fixterminal.terminal.business.senders;

import com.fixterminal.shared.orders.RxOrderEntity;
import com.fixterminal.terminal.business.application.RxQuickFixTerminal;
import com.fixterminal.terminal.business.factories.RxQFixNewOrderSingleFactory;
import com.fixterminal.terminal.business.services.RxMessageDecorator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quickfix.Session;
import quickfix.SessionNotFound;

@Component
@Slf4j
public class RxOrderMessageSender {

    RxQuickFixTerminal quickFixTerminal;
    RxTerminalOrderController rxTerminalOrderController;

    @Autowired
    public RxOrderMessageSender(RxQuickFixTerminal quickFixTerminal,
                                RxTerminalOrderController rxTerminalOrderController){
        log.info("Init : "+this.getClass().getSimpleName());
        this.quickFixTerminal = quickFixTerminal;
        this.rxTerminalOrderController = rxTerminalOrderController;
    }


    public String sendNewOrderSingle(RxOrderEntity rxOrder) throws Exception {

        rxTerminalOrderController.orderCheck(rxOrder);

        quickfix.Message message =
                RxQFixNewOrderSingleFactory.create(rxOrder);

        //TODO debug order
        System.out.println("ORDER_MESSAGE_SENDER>NEW_ORDER_SINGLE>"+ RxMessageDecorator.decorate(message));


       //TODO OrderMenager.  kontrola otwierania zlecenń jeżeli nie zwroyu exex raport.

        try {
            Session.sendToTarget(message,quickFixTerminal.getTradeSessionsId());
            return rxOrder.getClOrdID();
        } catch (SessionNotFound e) {
            e.printStackTrace();
            return null;
        }

    }


}
