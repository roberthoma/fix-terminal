package com.fixterminal.market.business.calculators;


import com.fixterminal.shared.enumerators.RxEntryTypeEnum;
import com.fixterminal.shared.enumerators.RxMDUpdateActionEnum;
import com.fixterminal.shared.market.RxMarketDataCalcBaseVO;
import com.fixterminal.shared.market.RxMarketDataVO;

import java.util.Map;

public class RxCalculatorMarketDataBase {

    public static void calculatePrices(RxMarketDataCalcBaseVO mdCalcVo ,
                                       Map<String, RxMarketDataVO> mdOfferMap)
    {

        mdOfferMap.forEach((s, md) ->
                {
                    if( mdCalcVo.dateTime == null) {
                        mdCalcVo.dateTime = md.entryDateTime;
                    }

                    if ( md.action == RxMDUpdateActionEnum.NEW
                            || md.action == RxMDUpdateActionEnum.CHANGE
                       )
                    {
                        if (md.entryType == RxEntryTypeEnum.BID) {
                            if (mdCalcVo.bidBestPrice == 0L || mdCalcVo.bidBestPrice < md.entryPx)
                            {
                                mdCalcVo.bidBestPrice = md.entryPx;
                            }
                            mdCalcVo.bidCounter++;
                            mdCalcVo.bidSize += md.entrySize;
                        }


                        if (md.entryType == RxEntryTypeEnum.OFFER){
                            if(mdCalcVo.offerBestPrice == 0L || mdCalcVo.offerBestPrice > md.entryPx)
                            {
                                mdCalcVo.offerBestPrice = md.entryPx;
                            }
                            mdCalcVo.offerCounter++;
                            mdCalcVo.offerSize += md.entrySize;
                        }
                    }
                }
        );

        mdCalcVo.spread = mdCalcVo.offerBestPrice - mdCalcVo.bidBestPrice;
    }

}
