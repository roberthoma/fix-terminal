package com.fixterminal.terminal.business.senders;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.enumerators.RxOrderStatus;
import com.fixterminal.shared.enumerators.RxOrderType;
import com.fixterminal.shared.keys.RxTypeOrderKey;
import com.fixterminal.shared.market.RxExecuteReport;
import com.fixterminal.shared.orders.RxOrderEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class RxTerminalOrderController {

    private final Map<RxTypeOrderKey, RxOrderStatus> prevOrderStatusMap = new HashMap<>();
    private final Map<String, RxOrderEntity> orderMap = new HashMap<>();

    RxTerminalOrderController() {
        log.info("Init : " + this.getClass().getSimpleName());
    }


    private boolean isPrevOrderAccepted(RxTypeOrderKey ordKey) {
        printMaps();
        if (!prevOrderStatusMap.containsKey(ordKey)) {
            return true;
        }
        RxOrderStatus order = prevOrderStatusMap.get(ordKey);

        //if (order.getType().equals(RxOrderType.MARKET)) {
        if ( ordKey.getType().equals(RxOrderType.MARKET)) {
            return prevOrderStatusMap.get(ordKey).compareTo(RxOrderStatus.FILLED) == 0;
        }

        //For others  RxOrderType.STOP || STOP_LIMIT etc
        return prevOrderStatusMap.get(ordKey).equals(RxOrderStatus.NEW)
            || prevOrderStatusMap.get(ordKey).equals(RxOrderStatus.FILLED);

    }


    public void orderCheck(RxOrderEntity rxOrder) throws Exception{

        RxTypeOrderKey ordKey = new RxTypeOrderKey(rxOrder);

        if (!isPrevOrderAccepted(ordKey)){
            printMaps();
                throw new Exception("!!!! TERMINAL WAITING FOR EXEC REPORT !!!!!!!!");
        }
        rxOrder.setOrderStatus(RxOrderStatus.SENT);
        prevOrderStatusMap.put(ordKey, rxOrder.getOrderStatus());
        orderMap.put(rxOrder.getClOrdID(), rxOrder);

        printMaps();

    }

    public void executeReportCheck(RxExecuteReport exeReport){

    //    RxTypeOrderKey orderKey =   new RxTypeOrderKey(exeReport.getInstrument().getFixSymbol(),exeReport.getOrderType());
//        System.out.println("SZUKAM >  "+orderKey);
//        System.out.println(">>> orderMap.size = "+ orderMap.size());

        if (orderMap.containsKey(exeReport.getClOrdID())) {

            System.out.println("TERMINAL>ORDER_CONTROLLER>EXECUTION_REPORT_CHECK> "+exeReport);

            RxTypeOrderKey key  = new RxTypeOrderKey(exeReport.getInstrument(), exeReport.getOrderType());
            prevOrderStatusMap.put(key,exeReport.getOrderStatus());

            if (exeReport.getOrderStatus().equals(RxOrderStatus.FILLED)
            && exeReport.getOrderType().equals(RxOrderType.STOP)) {
                orderMap.remove(exeReport.getClOrdID());
            }
            else {
                orderMap.get(exeReport.getClOrdID()).setOrderStatus(exeReport.getOrderStatus());
            }


        }
        else {
            System.out.println(">>>>>>>>>>>  ORDER NIESTNIEJE  <<<<<<< ");
            System.out.println("> " +exeReport.getOrderType()+" " +
                                     exeReport.getInstrument().getSymbol()+" " +
                    exeReport.getOrderId());

       }

        printMaps();

    }

   private void printMaps(){
       System.out.println("-----TERMINAL_ORDER_CTR prevOrderStatusMap ----");
       prevOrderStatusMap.forEach((rxTypeOrderKey, xOrd) -> System.out.println(rxTypeOrderKey+" : "+xOrd));
       System.out.println("--- orderMap ---");
       orderMap.forEach((s, rxOrd) -> System.out.println(s+"="+rxOrd));
       System.out.println("---------------------------------------");

   }

}

