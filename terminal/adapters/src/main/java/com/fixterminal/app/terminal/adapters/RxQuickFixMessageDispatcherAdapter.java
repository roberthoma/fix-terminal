package com.fixterminal.app.terminal.adapters;

import com.fixterminal.app.terminal.business.RxQuickFixMessageDispatcher;
import com.fixterminal.shared.market.RxExecuteReport;
import com.fixterminal.shared.market.RxMarketDataVO;
import com.fixterminal.shared.positions.RxPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Component
public class RxQuickFixMessageDispatcherAdapter implements RxQuickFixMessageDispatcherPort{

    @Autowired
     RxQuickFixMessageDispatcher messageDispatcher;

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
