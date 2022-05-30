package com.fixterminal.market.business.trade.actions;

import com.fixterminal.shared.enumerators.RxActionType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RxActionKey {
   String instrumentFixSymbol;
   RxActionType action;

   @Override
   public int hashCode() {
      return Integer.hashCode(this.instrumentFixSymbol.hashCode())+action.hashCode();
   }

   @Override
   public String toString(){
      return "Symbol=" + instrumentFixSymbol + " type="+action.toString();
   }

   @Override
   public boolean equals(Object o) {
      if((o == null) || (getClass() != o.getClass())){
         return false;
      }
      if (o == this) {
         return true;
      }

      RxActionKey key2 = (RxActionKey)o;
      return instrumentFixSymbol.compareTo(key2.instrumentFixSymbol) == 0
              && action.equals(key2.getAction());
   }

}

