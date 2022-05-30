package com.fixterminal.shared.keys;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.enumerators.RxOrderType;
import com.fixterminal.shared.orders.RxOrderEntity;
import lombok.Getter;

public class RxTypeOrderKey implements Comparable{

    @Getter
     private RxOrderType type;
    @Getter
    private String instrumentSymbol;
    public RxTypeOrderKey(RxInstrument instrument, RxOrderType type){
        this.type = type;
        this.instrumentSymbol = instrument.getFixSymbol();
    }

    public RxTypeOrderKey(RxOrderEntity rxOrder){
        this.type =  rxOrder.getType();
        this.instrumentSymbol = rxOrder.getFixSymbol();
    }


    @Override
    public String toString(){
        return "Symbol=" +instrumentSymbol + " type="+type.toString();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.type.hashCode())+instrumentSymbol.hashCode();
    }
    @Override
    public int compareTo(Object o) {
    //    System.out.println(">>> RxTypeOrderKey START ");
        RxTypeOrderKey key2 = (RxTypeOrderKey)o;
        return instrumentSymbol.compareTo(key2.instrumentSymbol)  +
                type.compareTo(key2.type);
    }
    @Override
    public boolean equals(Object o) {
    //    System.out.println(">>> RxTypeOrderKey START ");
        boolean isEq;
        if((o == null) || (getClass() != o.getClass())){
     //       System.out.println(">>> RxTypeOrderKey FALSE 1 ");
            return false;
        }
        if (o == this) {
//            System.out.println(">>> RxTypeOrderKey TRUE ");
            return true;
        }
        RxTypeOrderKey key2 = (RxTypeOrderKey)o;
        isEq =  instrumentSymbol.compareTo(key2.instrumentSymbol) == 0
                && type.compareTo(key2.getType()) == 0;
         if (!isEq){
   //          System.out.println("equals >> key2.type ="+key2.getType());
         }
         else {
      //       System.out.println("equals >> is true");
         }

        return isEq;
    }
}
