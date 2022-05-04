package com.fixterminal.app.commands.menu.trade;

import com.fixterminal.app.commands.base.RxCmdAbstract;
import com.fixterminal.app.ports.RxMarketTraderPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RxTradeActionsFactory {

    RxMarketTraderPort trader;

    @Autowired
    RxTradeActionsFactory(RxMarketTraderPort trader){
      this.trader = trader;
    }

    public RxCmdAbstract createBuyMarketAction() {
        return new RxCmdAbstract() {
            @Override
            public void execute() throws Exception {
                trader.actionBuyMarket();
            }
        };
    }

    public RxCmdAbstract createSellMarketAction() {
        return new RxCmdAbstract() {
            @Override
            public void execute() throws Exception {
                trader.actionSellMarket();
            }
        };
    }

    public RxCmdAbstract createCloseAction() {
        return new RxCmdAbstract() {
            @Override
            public void execute() throws Exception {
                trader.actionClosePosition();
            }
        };
    }
}
