package com.fixterminal.app.commands.menu.trade;

import com.fixterminal.app.commands.base.RxCmdAbstract;
import com.fixterminal.app.ports.RxMarketTraderPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

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
            public void execute(Map<String, String> params) throws Exception {
                trader.actionBuyMarket(params);
            }
        };
    }

    public RxCmdAbstract createSellMarketAction() {
        return new RxCmdAbstract() {
            @Override
            public void execute(Map<String, String> params) throws Exception {
                trader.actionSellMarket(params);
            }
        };
    }

    public RxCmdAbstract createCloseAction() {
        return new RxCmdAbstract() {
            @Override
            public void execute(Map<String, String> params) throws Exception {
                trader.actionClosePosition(params);
            }
        };
    }

//    public RxCmdAbstract createBreakevenAction() {
//        return new RxCmdAbstract() {
//            @Override
//            public void execute(Map<String, String> params) throws Exception {
//                trader.actionBreakeven(params);
//            }
//        };
//    }

}
