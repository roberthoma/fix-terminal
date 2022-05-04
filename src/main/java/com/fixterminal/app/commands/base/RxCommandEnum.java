package com.fixterminal.app.commands.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//TODO Temat do wyjaśnieniea czy utrzymywać wszystkie poleceniea
public enum RxCommandEnum {

    ORD_BUY_MARKET("buy", RxCommandMenuGroupEnum.TRADE,"BUY by Market"),
    ORD_SELL_MARKET("sell", RxCommandMenuGroupEnum.TRADE,"SELL by Market"),
    ORD_REVERSE("reverse", RxCommandMenuGroupEnum.TRADE,"Reverse existing position"),
    ORD_CLOSE("close", RxCommandMenuGroupEnum.TRADE,"Close position"),
    ORD_BUY_LIMIT("buy_limit", RxCommandMenuGroupEnum.TRADE,"BUY by Limit"),
    ORD_SELL_LIMIT("sell_limit", RxCommandMenuGroupEnum.TRADE,"SELL by Limit"),

    REQ_ORDER_CANCEL("order_cancel", RxCommandMenuGroupEnum.TRADE,"Cancel Pending Order"),
    REQ_SET_BREAKEVEN("breakeven" , RxCommandMenuGroupEnum.TRADE, "Set Breakeven" ),


    LOGON("logon", RxCommandMenuGroupEnum.FILE,"Logon to serv"),
    LOGOUT("logout", RxCommandMenuGroupEnum.FILE,"Logout "),
    EXIT("exit", RxCommandMenuGroupEnum.FILE,"Exit from terminal"),

    SET("set", RxCommandMenuGroupEnum.CONFIGURATION,"Set or print configuration"),
    SET_VALUE_UP("set_up", RxCommandMenuGroupEnum.CONFIGURATION,"Set Value UP" ),
    SET_VALUE_DOWN("set_down", RxCommandMenuGroupEnum.CONFIGURATION,"Set Value DOWN" ),


    REQ_REFRESH("refresh", RxCommandMenuGroupEnum.TOOLS,"Refresh monitor"),
    REQ_POSITIONS_LIST("positions", RxCommandMenuGroupEnum.TOOLS,"Print open position"),



    TERM_START("start", RxCommandMenuGroupEnum.TERMINAL,"Stating FIX terminal"),
    TERM_CONNECT("connect", RxCommandMenuGroupEnum.TERMINAL,"Connecting to FIX terminal server"),
    SESSION("session", RxCommandMenuGroupEnum.TERMINAL,"Print or set sessions parameters"),
    //TODO shoutdown terminal server :)
    HELP("help", RxCommandMenuGroupEnum.HELP,"This help"),
    ABOUT("about", RxCommandMenuGroupEnum.HELP,"About author , libraries etc. "),
    COMMAND_ERROR("command_error", RxCommandMenuGroupEnum.NO_GROUP,"ERROR commnad");


    String symbol;
    String shortcutKey;
    String description;
    ArrayList<String> optionsList;
    int helpOrder;
    RxCommandMenuGroupEnum menuGroup;

    private static Map<String, RxCommandEnum> mapBySymbol= new HashMap<>();

    RxCommandEnum(String cmdSymbol, RxCommandMenuGroupEnum menuGroup, String description){
        this.symbol = cmdSymbol;
        this.menuGroup = menuGroup;
        this.description = description;
        optionsList= new ArrayList<>();
        optionsList.add("-h");
        optionsList.add("--help");

    }
    RxCommandEnum(String cmdSymbol, RxCommandMenuGroupEnum menuGroup, String description, String options){
        this(cmdSymbol, menuGroup, description);
        optionsList.addAll(Arrays.asList(options.split(";")));
    }

    public String getSymbol(){
        return symbol;
    };

    public RxCommandMenuGroupEnum getMenuGroup(){
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
