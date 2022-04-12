package com.fixterminal.terminal.business.services;

import quickfix.Message;

public class RxMessageDecorator {

public static String decorate(Message message){
return message.toString().replace ("\001","|");
}

}
