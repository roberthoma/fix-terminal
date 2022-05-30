package com.fixterminal.market.business.trade.orders;

import com.fixterminal.market.business.trade.actions.RxActionsManager;
import com.fixterminal.market.ports.RxMessageSenderPort;
import com.fixterminal.market.ports.RxQuickFixMessageDispatcherPort;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.enumerators.RxExecType;
import com.fixterminal.shared.enumerators.RxOrderStatus;
import com.fixterminal.shared.enumerators.RxOrderType;
import com.fixterminal.shared.market.RxExecuteReport;
import com.fixterminal.shared.orders.RxOrderEntity;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RxOrdersManager {
    Map<String, RxOrderEntity> ordersMap = new HashMap<>();
    RxQuickFixMessageDispatcherPort msgDispatcher;
    RxMessageSenderPort messageSender;

    @Setter
    Consumer<RxExecuteReport> executeReportConsumer;

    @Autowired
    RxOrdersManager(RxQuickFixMessageDispatcherPort msgDispatcher,
                    RxMessageSenderPort messageSender) {
        log.info("Init : RxOrdersManager ");
        this.msgDispatcher = msgDispatcher;
        this.messageSender = messageSender;

        msgDispatcher.setExecutionReportConsumer(this::executeReportConsume);

    }


    public void ordersMapPrint() {
        if (!ordersMap.isEmpty()) {
            System.out.println("> --ordersMap --");
            ordersMap.forEach((s, rxOrderEntity) -> System.out.println(rxOrderEntity));
        } else {
            System.out.println(">  ordersMap is  empty");
        }


    }

    public void executeReportConsume(RxExecuteReport rxExecuteReport) {
        System.out.println("ORDERS_MANAGER>EXECUTE_REPORT_CONSUMER>" + rxExecuteReport);

        System.out.println("ordersMap>>" + ordersMap);

//        if (rxExecuteReport.getOrderType().equals(RxOrderType.MARKET)) {

            if (ordersMap.containsKey(rxExecuteReport.getClOrdID())) {
                ordersMap.get(rxExecuteReport.getClOrdID()).setOrderStatus(rxExecuteReport.getOrderStatus());
            }
//TODO cat exec to order            else{
//                ordersMap.put(rxExecuteReport.getClOrdID(), ?????
//            }

            if (rxExecuteReport.getOrderStatus().equals(RxOrderStatus.FILLED)) {
                messageSender.sendRequestForPositions();
            }

        if (rxExecuteReport.getExecType().equals(RxExecType.CANCEL)){
            ordersMap.remove(rxExecuteReport.getOrigClOrdID());
        }
        System.out.println("????Przed accept(rxExecuteReport) ");
        executeReportConsumer.accept(rxExecuteReport);


    }

    public void put(RxOrderEntity order) {
        System.out.println("ORDERS_MANAGER>put "+order.getClOrdID() +" "+order.getType());
      ordersMap.put(order.getClOrdID(),order);
  //    orderTypeMap.put(new RxTypeOrderKey(order),order);
    }


    public RxOrderEntity getStopLossOrder(RxInstrument instrument) {
        ordersMapPrint();

        List<RxOrderEntity> slOrder = ordersMap.values()
                                               .stream()
                .filter(rxOrderEntity -> rxOrderEntity.getType().equals(RxOrderType.STOP))
                .filter(rxOrderEntity -> !rxOrderEntity.getOrderStatus().equals(RxOrderStatus.FILLED))
                .collect(Collectors.toList());

        if (slOrder.size() == 0 ) {
            System.out.println("NO SL >>> ");
            return null;
        }


        if (slOrder.size() > 1) {
            System.out.println("E E E  E EE R RR   R R R O RR R RRRRRR >>> ");
        }


        return  slOrder.get(0);
    }
}
