package com.fixterminal.terminal.business.services;

import com.fixterminal.shared.dictionaries.RxDictionaries;
import com.fixterminal.shared.dictionaries.instruments.RxDicInstruments;
import com.fixterminal.shared.enumerators.RxMDUpdateActionEnum;
import com.fixterminal.shared.market.RxMarketDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quickfix.FieldNotFound;
import quickfix.Message;
import quickfix.SessionID;
import quickfix.field.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RxMarketDataService {

    @Autowired
    static
    RxDicInstruments instrumentsDic;

    public static List<RxMarketDataVO> messageToList(Message message, SessionID sessionId) throws FieldNotFound {


        List<RxMarketDataVO> mdList = new ArrayList();
        LocalDateTime msgTime;


        quickfix.fix44.MarketDataSnapshotFullRefresh.NoMDEntries group = new quickfix.fix44.MarketDataSnapshotFullRefresh.NoMDEntries();


        NoMDEntries noMDEntries = new NoMDEntries();

        message.getField(noMDEntries);

        msgTime = message.getHeader().getUtcTimeStamp(SendingTime.FIELD);

        noMDEntries.getField();
        for (int i = 1; i <=  noMDEntries.getValue(); i++) {
            RxMarketDataVO mdVO = new RxMarketDataVO();

            message.getGroup(i, group);
            mdVO.entryID = group.getString(MDEntryID.FIELD); //278=421395471

            if (group.isSetField(MDUpdateAction.FIELD)) {
                mdVO.action = RxMDUpdateActionEnum.of  (group.getChar(MDUpdateAction.FIELD));
            }
            else {
                mdVO.action = RxMDUpdateActionEnum.NEW; //Only for full refresh
            }
                if (group.isSetField(MDEntryType.FIELD)) {
                mdVO.entryType = RxTypeCastService.fixEntryTypeToRxEntryType(group.getMDEntryType());
            }
            if (group.isSetField(MDEntryPx.FIELD)) {
                mdVO.entryPx = group.getDouble(MDEntryPx.FIELD);// 1.21739);     //       270=1.21739
            }

            if (group.isSetField(MDEntrySize.FIELD)) {
                mdVO.entrySize = group.getDouble(MDEntrySize.FIELD);   //      271=3000000
            }

            if (group.isSetField(Symbol.FIELD)) {
                mdVO.instrument = instrumentsDic.getByFixSymbol(group.getString(Symbol.FIELD));
            }
            else if(message.isSetField(Symbol.FIELD)) {
                mdVO.instrument = instrumentsDic.getByFixSymbol(message.getString(Symbol.FIELD));
            }
            else{
                System.out.println("WARNING: Symbol field not set :");
                System.out.println(message);
            }

            mdVO.entryDateTime = msgTime;

            mdList.add(mdVO);
        }
        return mdList;

    }



    public void snapshotFullRefresh(Message message, SessionID sessionId) {
    }
}
