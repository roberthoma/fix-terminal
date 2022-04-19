package com.fixterminal.terminal.domain.services;

import quickfix.Message;

public class RxMessageDecorator {

public static String decorate(Message message){
return message.toString().replace ("\001","|");
}

}
