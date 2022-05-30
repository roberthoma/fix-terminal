package com.fixterminal.shared.orders;


import com.fixterminal.shared.enumerators.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.util.Precision;

@Getter
@Setter
public class RxOrderEntity {  //implements Cloneable {
  //  private SessionID sessionID = null;
    private String clOrdID = null;    // ClOrdID <11>
   // private String originalID = null;

    private String fixSymbol = null;  //TODO Instrument symbol
    private Double quantity;
    private int open = 0;
    private double executed = 0;
    private RxOrderSide side ;
    private RxOrderType type ;
    private RxOrderTimeInForce tif;
    private RxOrderStatus orderStatus;
    private Double price = null;
//    private Double stop = null;
    private double avgPx = 0.0;



    private String message = null;
    private static int nextID = 1;

    public RxOrderEntity() {
        clOrdID = generateID();
        orderStatus = RxOrderStatus.CREATED;
    }
    public RxOrderEntity(RxActionType actionType) {
        clOrdID = generateID(actionType);
        orderStatus = RxOrderStatus.CREATED;
    }

//    public Object clone() {
//        try {
//            RxOrderEntity rxOrderEntity = (RxOrderEntity) super.clone();
//  //          order.setOriginalID(getID());
//  //          order.setID(order.generateID());
//            return rxOrderEntity;
//        } catch (CloneNotSupportedException e) {}
//        return null;
//    }

    @Override
    public String toString(){
      return "ID="+ clOrdID +" ; fixSymbol="+fixSymbol+" ; type="+type+ " ; quantity="+quantity+" ; Status="+orderStatus;
    }

    public String generateID() {
        return Long.toString(System.currentTimeMillis() + (nextID++)); // ??? to będzi enieładna suma
    }

    public String generateID(RxActionType actionType) {
        if( actionType.equals(RxActionType.STOP_LOSS)) {
            return "#SL#"+Long.toString(System.currentTimeMillis() + (nextID++)); // ??? to będzi enieładna suma
        }
        return Long.toString(System.currentTimeMillis() + (nextID++)); // ??? to będzi enieładna suma

    }


    public void setPrice (double price) {
            this.price = Precision.round(price,5); //TODO precision from intrument
    }



}
