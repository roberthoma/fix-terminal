package com.fixterminal.terminal.business.propmters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class RxFixHeartPrompter implements Consumer<String> {
    private static final Logger log = LoggerFactory.getLogger(RxFixHeartPrompter.class);
    String heartBB;
    public RxFixHeartPrompter(){
        log.info("Init : RxFixHeartPrompter");
    }

    @Override
    public void accept(String s) {
        heartBB = s;
        log.info(s);
    }

    public String getLog(){
        return heartBB;
    }
}

