package com.fixterminal.strategies.business.trader.controllers;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.strategies.business.parameters.RxTradeParametersDesk;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class RxTradeControllersDesk {

    RxTradeParametersDesk rxTradeParametersDesk;
    private Map<RxInstrument, RxTradeController> tradeControllersMap;

    @Autowired
    public RxTradeControllersDesk(RxTradeParametersDesk rxTradeParametersDesk){
      this.rxTradeParametersDesk = rxTradeParametersDesk;
      log.info("Init : " + this.getClass().getSimpleName());

        tradeControllersMap = new HashMap<>();


    }


    public void start() {
       //todo initcjacja controlerów dla
       log.info(" INFO Z DESKO CONN...");
    }

}
