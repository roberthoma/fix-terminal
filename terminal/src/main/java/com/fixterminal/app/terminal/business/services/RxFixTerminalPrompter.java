package com.fixterminal.app.terminal.business.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class RxFixTerminalPrompter implements Consumer<String> {
    private static final Logger log = LoggerFactory.getLogger(RxFixTerminalPrompter.class);

    private StringBuffer logBuffer = new StringBuffer();


    public RxFixTerminalPrompter(){
     log.info("Init : RxFixTerminalPrompter");
    }

    @Override
    public void accept(String s) {
        log.info(s);
        logBuffer.append(s).append("\n");
    }

    public String getLog(){

    return logBuffer.toString();
    }

}
