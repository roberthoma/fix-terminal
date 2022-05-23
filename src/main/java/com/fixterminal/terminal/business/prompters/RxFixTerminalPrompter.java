package com.fixterminal.terminal.business.prompters;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
public class RxFixTerminalPrompter implements Consumer<String> {
    private StringBuffer logBuffer = new StringBuffer();

    public RxFixTerminalPrompter(){
     log.info("Init : RxFixTerminalPrompter");
    }

    @Override
    public void accept(String s) {
            log.info(s);
            //logBuffer.append(s).append("\n");
    }

    public String getLog(){

    return logBuffer.toString();
    }

}
