package com.fixterminal.commands.base;

import com.fixterminal.commands.menu.trade.RxCmdBuyMarket;
import com.fixterminal.commands.menu.trade.RxCmdSellMarket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class RxCommandDispatcher {

    Map<RxCommandsEnum, RxCmdAbstract> cmdMap       = new HashMap();
    Map<String, RxCommandsEnum>        cmdSymbolMap = new HashMap();

    int helpOrd = 0;
    private RxCommandDispatcher() {
        log.info("Init : RxCommandDispatcher");
        init();
    }
    private  void put(RxCommandsEnum cmdId, RxCmdAbstract cmd ){
        helpOrd++; //TODO a może wstawiać jeszcze do listy , która utrzyma kolejnośc
        cmdId.setHelpOrder(helpOrd);
        cmdMap.put(cmdId,cmd );
        cmdSymbolMap.put(cmdId.symbol,cmdId);

        //TODO Warunkowe drukowanie np na parametr
        // System.out.println(" > Init cmd :" +cmdId.getSymbol()+" - " +cmdId.getDescription());
    }


    private  void init() {
        System.out.println("Start commands initialisation");

        put(RxCommandsEnum.ORD_BUY_MARKET, new RxCmdBuyMarket());
        put(RxCommandsEnum.ORD_SELL_MARKET, new RxCmdSellMarket());

    }
    public void dispose(RxCommandsEnum cmd) {
        dispose(cmd, null);

    }
    public void dispose(RxCommandsEnum cmd, List<RxCmdParameter> cmdParameters) {
        try {
            if (cmdMap.containsKey(cmd)) {
                System.out.println("");
                cmdMap.get(cmd).execute();
                System.out.println("");
//            } else {
//                System.out.println(" Commend NOT implements yet !!! :(  ");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public RxCommandsEnum decodeCommand(String cmd){

       return RxCommandsEnum.ORD_BUY_MARKET;
    }

//    public  List or map Parameters  decodeParameters(String cmd){
//
//        return RxCommandsEnum.ORD_BUY_MARKET;
//    }


}
