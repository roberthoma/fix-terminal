package com.fixterminal.app.commands.base;

import com.fixterminal.app.commands.menu.trade.RxTradeActionsFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class RxCommandDispatcher {

    Map<RxCommandEnum, RxCmdAbstract> cmdMap       = new HashMap();
    Map<String, RxCommandEnum>        cmdSymbolMap = new HashMap();

    RxTradeActionsFactory tradeActionFactory;


    int helpOrd = 0;
    @Autowired
    private RxCommandDispatcher(RxTradeActionsFactory tradeActionFactory) {
        log.info("Init : RxCommandDispatcher");
        this.tradeActionFactory =tradeActionFactory;
        init();
    }
    private  void put(RxCommandEnum cmdId, RxCmdAbstract cmd ){
        helpOrd++; //TODO a może wstawiać jeszcze do listy , która utrzyma kolejnośc
        cmdId.setHelpOrder(helpOrd);
        cmdMap.put(cmdId,cmd );
        cmdSymbolMap.put(cmdId.getSymbol(),cmdId);

        //TODO Warunkowe drukowanie np na parametr
        // System.out.println(" > Init cmd :" +cmdId.getSymbol()+" - " +cmdId.getDescription());
    }


    private  void init() {

        put(RxCommandEnum.ORD_BUY_MARKET, tradeActionFactory.createBuyMarketAction());
        put(RxCommandEnum.ORD_SELL_MARKET, tradeActionFactory.createSellMarketAction());
        put(RxCommandEnum.ORD_CLOSE, tradeActionFactory.createCloseAction());

    }
    public void dispose(RxCommandEnum cmd) {
        dispose(cmd, null);

    }
    public void dispose(RxCommandEnum cmd, List<RxCmdParameter> cmdParameters) {
        try {
            if (cmdMap.containsKey(cmd)) {
                cmdMap.get(cmd).execute();
            } else {
               throw new RxCmdNotImplementedException("command : "+cmd);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public RxCommandEnum decodeCommand(String cmd){
      if (cmdSymbolMap.containsKey(cmd)){
         return cmdSymbolMap.get(cmd);
      }
        return RxCommandEnum.COMMAND_ERROR;
    }

//    public  List or map Parameters  decodeParameters(String cmd){
//
//        return RxCommandsEnum.ORD_BUY_MARKET;
//    }


}
