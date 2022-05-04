package com.fixterminal.app.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RxAbout {

    public List<String> getAbout(){
    List<String> aboutList =  new ArrayList<>();
    aboutList.add("FIXTerminal command line tool for FOREX scalping.");
    aboutList.add("ENGINE :  + ${fixEngineInfo}");
    aboutList.add("https://www.quickfixj.org/documentation/license.html");
    aboutList.add("author: Robert Homa ");
    aboutList.add("email : roberthoma@yahoo.com");

    return aboutList;
    }
}