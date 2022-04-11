package com.fixterminal.shared.orders;


import com.fixterminal.shared.enumerators.RxOrderSide;
import com.fixterminal.shared.enumerators.RxOrderTimeInForce;
import com.fixterminal.shared.enumerators.RxOrderType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.util.Precision;

@Getter
@Setter
public class RxOrderEntity implements Cloneable {
  //  private SessionID sessionID = null;
    private String symbol = null;  //TODO Instrument symbol
    private Double quantity;
    private int open = 0;
    private double executed = 0;
    private RxOrderSide side ;
    private RxOrderType type ;
    private RxOrderTimeInForce tif;
    private Double price = null;
//    private Double stop = null;
    private double avgPx = 0.0;

//    private boolean rejected = false;
//    private boolean canceled = false;
//    private boolean isNew = true;


    private String message = null;
    private String ID = null;
    private String originalID = null;
    private static int nextID = 1;

    public RxOrderEntity() {
        ID = generateID();
    }

    public RxOrderEntity(String ID) {
        this.ID = ID;
    }

    public Object clone() {
        try {
            RxOrderEntity rxOrderEntity = (RxOrderEntity) super.clone();
  //          order.setOriginalID(getID());
  //          order.setID(order.generateID());
            return rxOrderEntity;
        } catch (CloneNotSupportedException e) {}
        return null;
    }

    public String generateID() {
        return Long.toString(System.currentTimeMillis() + (nextID++));
    }


    public void setPrice (double price) {
            this.price = Precision.round(price,5); //TODO precision from intrument
    }



}
