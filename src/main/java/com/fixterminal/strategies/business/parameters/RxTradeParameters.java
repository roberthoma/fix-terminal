package com.fixterminal.strategies.business.parameters;

import com.fixterminal.shared.enumerators.RxOnOff;
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
// marametry mogą być manippulowane przez menagera i manulanie przez tradera(osobe fizyczną)

//TODO przygotować klase abstrakcyjną i utrzymywać parametry w mapie itd
@Setter
@Getter
public class RxTradeParameters {

    private Double quantity;
    private RxOrderType type;
    private RxOrderTimeInForce tif;
    private Double price;

    // Parameters configurable from file
    private RxOnOff AUTO_BREAKEVEN;
    private double  BREAKEVEN_ACTIVATE_DIST;
    private double  BREAKEVEN_PROFIT;
    private double  STOP_LOSS_DISTANCE;
    private double  QUANTITY;
    private double  MOVE_DISTANCE;




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

    @Override
    public String toString() {
        String paramsList;

        paramsList = " AUTO_BREAKEVEN = "+ AUTO_BREAKEVEN.toString() + "\n"+
                     " BREAKEVEN_ACTIVATE_DIST = "+ Double.valueOf(BREAKEVEN_ACTIVATE_DIST).toString();



        return paramsList;
    }
}
