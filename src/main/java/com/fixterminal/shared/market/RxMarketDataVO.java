package com.fixterminal.shared.market;


import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.enumerators.RxEntryTypeEnum;
import com.fixterminal.shared.enumerators.RxMDUpdateActionEnum;

import java.time.LocalDateTime;

//https://www.onixs.biz/fix-dictionary/4.4/tagNum_269.html
public class RxMarketDataVO {

    public RxMDUpdateActionEnum action;
    public String               entryID ;   //        278=421395471
    public RxEntryTypeEnum entryType;          // 269=1
    public double               entryPx;     //       270=1.21739
    public double               entrySize;   //      271=3000000
    public LocalDateTime        entryDateTime;
    public RxInstrument instrument;
//    public int entryStatus;



}
