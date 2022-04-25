package com.fixterminal.commands.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum RxCommandsEnum {

    ORD_BUY_MARKET("buy", RxMenuGroupEnum.TRADE,"BUY by Market"),
    ORD_SELL_MARKET("sell", RxMenuGroupEnum.TRADE,"SELL by Market"),
    ORD_REVERSE("reverse", RxMenuGroupEnum.TRADE,"Reverse existing position"),
    ORD_CLOSE("close", RxMenuGroupEnum.TRADE,"Close position"),
    ORD_BUY_LIMIT("buy_limit", RxMenuGroupEnum.TRADE,"BUY by Limit"),
    ORD_SELL_LIMIT("sell_limit", RxMenuGroupEnum.TRADE,"SELL by Limit"),

    REQ_ORDER_CANCEL("order_cancel",RxMenuGroupEnum.TRADE,"Cancel Pending Order"),
    REQ_SET_BREAKEVEN("breakeven" ,RxMenuGroupEnum.TRADE, "Set Breakeven" ),


    LOGON("logon", RxMenuGroupEnum.FILE,"Logon to serv"),
    LOGOUT("logout", RxMenuGroupEnum.FILE,"Logout "),
    EXIT("exit", RxMenuGroupEnum.FILE,"Exit from terminal"),

    SET("set", RxMenuGroupEnum.CONFIGURATION,"Set or print configuration"),
    SET_VALUE_UP("set_up",RxMenuGroupEnum.CONFIGURATION,"Set Value UP" ),
    SET_VALUE_DOWN("set_down",RxMenuGroupEnum.CONFIGURATION,"Set Value DOWN" ),


    REQ_REFRESH("refresh", RxMenuGroupEnum.TOOLS,"Refresh monitor"),
    REQ_POSITIONS_LIST("positions", RxMenuGroupEnum.TOOLS,"Print open position"),



    TERM_START("start",RxMenuGroupEnum.TERMINAL,"Stating FIX terminal"),
    TERM_CONNECT("connect",RxMenuGroupEnum.TERMINAL,"Connecting to FIX terminal server"),
    SESSION("session",RxMenuGroupEnum.TERMINAL,"Print or set sessions parameters"),
    //TODO shoutdown terminal server :)
    HELP("help", RxMenuGroupEnum.HELP,"This help"),
    ABOUT("about", RxMenuGroupEnum.HELP,"About author , libraries etc. "),
    COMMAND_ERROR("command_error", RxMenuGroupEnum.NO_GROUP,"ERROR commnad");


    String symbol;
    String shortcutKey;
    String description;
    ArrayList<String> optionsList;
    int helpOrder;
    RxMenuGroupEnum menuGroup;

    private static Map<String, RxCommandsEnum> mapBySymbol= new HashMap<>();

    RxCommandsEnum(String cmdSymbol, RxMenuGroupEnum menuGroup, String description){
        this.symbol = cmdSymbol;
        this.menuGroup = menuGroup;
        this.description = description;
        optionsList= new ArrayList<>();
        optionsList.add("-h");
        optionsList.add("--help");

    }
    RxCommandsEnum(String cmdSymbol, RxMenuGroupEnum menuGroup, String description, String options){
        this(cmdSymbol, menuGroup, description);
        optionsList.addAll(Arrays.asList(options.split(";")));
    }

    public String getSymbol(){
        return symbol;
    };

    public RxMenuGroupEnum getMenuGroup(){
        return menuGroup;
    }
//    public void setShortcutKey(String shortcutKey){
//        this.shortcutKey = shortcutKey;
//    }
    public void setHelpOrder(int order){
        this.helpOrder = order;
    }
    public int getHelpOrder(){
        return  this.helpOrder ;
    }
    public ArrayList<String> getOptionsList(){return  this.optionsList;}


    public String getDescription() {
       return  description;
    }

    public String getShortcutKey() {
        if (shortcutKey == null){
            return "";
        }
        return  shortcutKey;
    }

}
