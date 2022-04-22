package com.fixterminal.strategies.business.parameters;

import com.fixterminal.shared.enumerators.RxOrderTimeInForce;
import com.fixterminal.shared.enumerators.RxOrderType;
import lombok.Getter;
import lombok.Setter;

//TODO klasa abstracyjan z parametrami i opcjami
//up
//down
//set_value
//default value
//current_value
//
//
//Ponadto parametry zapisywane pod konkretny instrumen


@Setter
@Getter
public class RxTradeParameters {

    private Double quantity;
    private RxOrderType type;
    private RxOrderTimeInForce tif;
    private Double price;



    //TODO     Breakeven params fo config file
    private boolean AUTO_BREAKEVEN = false; //: ON/OFF
    private double  BREAKEVEN_ACTIVATE_DIST  = 0.0002 ; //pips
    private double  BREAKEVEN_PROFIT         = 0.00006; // Poprawić na pips

    private double  STOP_LOSS_DISTANCE       = 0.0005;

    private double  QUANTITY                 = 1000;
    private double  MOVE_DISTANCE            = 1000;




//    New Order params
//    SL_ON_OPEN : 2pips
//    TP_ON_OPEN : 5pips
//    AUTO_SL : ON/OFF




    public RxTradeParameters(){
        this.quantity   = Double.valueOf(QUANTITY);
        this.type       = RxOrderType.MARKET;
        this.tif        = RxOrderTimeInForce.DAY;
    }


    //TODO  zmienne poprawiać lub umożliwić ustawianie na poziomie sesji 

   private void printValues(){


   }

   public void set_default(){

   }


}
