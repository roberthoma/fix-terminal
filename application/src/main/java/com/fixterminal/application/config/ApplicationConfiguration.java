package com.fixterminal.application.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {
        "com.fixterminal.shared",
        "com.fixterminal.terminal.business",
        "com.fixterminal.terminal.adapters",
        "com.fixterminal.monitors_adapters",
        "com.fixterminal.monitors",
//        "com.fixterminal.terminal.ports",
        "com.fixterminal.application",
        "com.fixterminal.gui"
})
public class ApplicationConfiguration {


//    @Bean
//    public RxDicBrokers init() {//throws ParserConfigurationException, IOException, SAXException {
//        try {
//
//            return InitDictionary.initDicBrokers();
//        }
//        catch (Exception e){
//            System.out.println(e);
//        }
//        return new RxDicBrokers();
//    }

}
