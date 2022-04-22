package com.fixterminal.market.ports;

import com.fixterminal.shared.market.RxExecuteReport;
import com.fixterminal.shared.market.RxMarketDataVO;
import com.fixterminal.shared.positions.RxPosition;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface RxQuickFixMessageDispatcherPort {

    void setMarketDataConsumer(BiConsumer<Boolean, List<RxMarketDataVO>> biMarketDataVOConsumer);

    void setPositionReportConsumer(Consumer<RxPosition> positionConsumer);


    void setExecutionReportConsumer(Consumer<RxExecuteReport> executeReportConsumer);

}
