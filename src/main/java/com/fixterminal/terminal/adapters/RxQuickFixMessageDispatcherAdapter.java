package com.fixterminal.terminal.adapters;

import com.fixterminal.market.ports.RxQuickFixMessageDispatcherPort;
import com.fixterminal.terminal.business.application.RxQuickFixMessageDispatcher;
import com.fixterminal.shared.market.RxExecuteReport;
import com.fixterminal.shared.market.RxMarketDataVO;
import com.fixterminal.shared.positions.RxPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Component
public class RxQuickFixMessageDispatcherAdapter implements RxQuickFixMessageDispatcherPort {
    private static final Logger log = LoggerFactory.getLogger(RxQuickFixMessageDispatcherAdapter.class);

     RxQuickFixMessageDispatcher messageDispatcher;

    @Autowired
    public RxQuickFixMessageDispatcherAdapter(RxQuickFixMessageDispatcher messageDispatcher){
    this.messageDispatcher = messageDispatcher;
       if (messageDispatcher !=null) {
           log.info("Init : RxQuickFixMessageDispatcherAdapter " + messageDispatcher.toString());
       }
       else {
           log.info("Init : RxQuickFixMessageDispatcherAdapter  messageDispatcher is NULL ");
       }
    }

    @Override
    public void setMarketDataConsumer(BiConsumer<Boolean, List<RxMarketDataVO>> biMarketDataVOConsumer) {
        messageDispatcher.setMarketDataConsumer(biMarketDataVOConsumer);
    }

    @Override
    public void setPositionReportConsumer(Consumer<RxPosition> positionConsumer) {
        messageDispatcher.setPositionReportConsumer(positionConsumer);
    }

    @Override
    public void setExecutionReportConsumer(Consumer<RxExecuteReport> executeReportConsumer) {
        messageDispatcher.setExecutionReportConsumer(executeReportConsumer);
    }
}
