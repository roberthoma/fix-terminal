package com.fixterminal.terminal.business.senders;

import com.fixterminal.shared.enumerators.RxOrderType;
import com.fixterminal.shared.orders.RxOrderEntity;

public class RxTypeOrderKey implements Comparable{

    public RxTypeOrderKey(String instrumentSymbol, RxOrderType type){
        this.type = type;
        this.instrumentSymbol = instrumentSymbol;
    }

    public RxTypeOrderKey(RxOrderEntity rxOrder){
        this.type =  rxOrder.getType();
        this.instrumentSymbol = rxOrder.getSymbol();
    }

    public RxOrderType type;
    public String instrumentSymbol;

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
        RxTypeOrderKey key2 = (RxTypeOrderKey)o;
        return instrumentSymbol.compareTo(key2.instrumentSymbol)  +
                type.compareTo(key2.type);
    }
    @Override

    public boolean equals(Object o) {
        //   System.out.println("ODPALAM equals => "+o);
        if((o == null) || (getClass() != o.getClass())){
            return false;
        }

        if (o == this) {
            return true;
        }

        RxTypeOrderKey key2 = (RxTypeOrderKey)o;
        return instrumentSymbol.compareTo(key2.instrumentSymbol) == 0
                && type.compareTo(key2.type) == 0;
    }


}
