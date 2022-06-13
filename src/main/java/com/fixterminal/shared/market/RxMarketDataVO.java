package com.fixterminal.shared.market;


import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.enumerators.RxEntryType;
import com.fixterminal.shared.enumerators.RxMDUpdateActionEnum;

import java.time.LocalDateTime;

//https://www.onixs.biz/fix-dictionary/4.4/tagNum_269.html
public class RxMarketDataVO {

    public String               entryID ;   //        278=421395471
    public RxMDUpdateActionEnum action;
    public RxEntryType entryType;          // 269=1
    public double               entryPx;     //       270=1.21739
    public double               entrySize;   //      271=3000000
    public LocalDateTime        entryDateTime;
    public RxInstrument instrument;
//    public int entryStatus;

   @Override
   public String toString(){

   return entryID + " " +
          instrument.getSymbol()+" " +
          action+ "  " +
          entryType + "  " +
          entrySize + "  " +
          entryPx + "  " +
          " ";
   }

}
