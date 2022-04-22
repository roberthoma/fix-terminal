package com.fixterminal.strategies.business.trader;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class RxTradeControllersDesk {

    private Map<RxInstrument, RxTradeController> tradeControllersMap;

    public RxTradeControllersDesk(){
      log.info("Init : " + this.getClass().getSimpleName());

        tradeControllersMap = new HashMap<>();


    }


    public void start() {
       log.info(" INFO Z DESKO CONN...");
    }
}
