package com.fixterminal.terminal.business.senders;

import com.fixterminal.shared.enumerators.RxOrderStatus;
import com.fixterminal.shared.market.RxExecuteReport;
import com.fixterminal.shared.orders.RxOrderEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class RxOrderController {

    private final Map<RxTypeOrderKey, RxOrderEntity> orderTypeMap = new HashMap<>();
    private final Map<String , RxOrderEntity> orderMap = new HashMap<>();

    RxOrderController(){
        log.info("Init : " + this.getClass().getSimpleName());
    }


    public void check(RxOrderEntity rxOrder) throws Exception{

        RxTypeOrderKey ordKey = new RxTypeOrderKey(rxOrder);

     //   System.out.println("OriginalID "+rxOrder.getOriginalID());

        if (orderTypeMap.containsKey(ordKey)) {
            System.out.println("JEST "+ordKey.type+" WARTOSC = "+ orderTypeMap.get(ordKey).getOrderStatus());
            if (orderTypeMap.get(ordKey).getOrderStatus().compareTo(RxOrderStatus.FILLED) != 0) {
                throw new Exception("TERMINAL WAITING FOR EXEC REPORT !!!!!!!!");
            }
        }
        rxOrder.setOrderStatus(RxOrderStatus.SENT);
        orderTypeMap.put(ordKey, rxOrder);
        orderMap.put(rxOrder.getID(), rxOrder);


        //orderMap.get(ordKey).equals(RxOrderStatus.NEW)

    }

    public void setStatus(RxExecuteReport exeReport){

    //    RxTypeOrderKey orderKey =   new RxTypeOrderKey(exeReport.getInstrument().getFixSymbol(),exeReport.getOrderType());
//        System.out.println("SZUKAM >  "+orderKey);
//        System.out.println(">>> orderMap.size = "+ orderMap.size());

        if (orderMap.containsKey(exeReport.getClOrdID())) {

//            System.out.println("ExecType => " + exeReport.getExecType());
//            System.out.println("OrderStatus => " + exeReport.getOrderStatus());
            System.out.println("ZMIANA NA "+exeReport.getOrderStatus());

            //exeReport.getOrderStatus()
            orderMap.get(exeReport.getClOrdID()).setOrderStatus(exeReport.getOrderStatus());

        }
        else {
            System.out.println(">>>>>>>>>>>  ORDER NIESTNIEJE  <<<<<<< ");
            System.out.println("> " +exeReport.getOrderType()+" " +
                                     exeReport.getInstrument().getSymbol()+" " +
                    exeReport.getOrderId());

       }



    }

}





//Map<,>
//
//
//potrzebuje ,,
//
//statusy
//wysłane
//zrealizowane
//anulowane itp
//
//jeżeli jest proba ponownego wysłaniea np wysłalnego a nie zrealizowanego . mam rzucić błąd i tyle



