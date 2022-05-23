package com.fixterminal.app.commands.base;

public class RxCmdNotImplementedException extends Exception{

    public RxCmdNotImplementedException(String message) {

        System.out.println(" >>>>> "+message +" >>>>>>>");
    }
}
