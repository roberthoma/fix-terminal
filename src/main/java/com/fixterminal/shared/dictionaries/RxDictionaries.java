package com.fixterminal.shared.dictionaries;

import com.fixterminal.shared.dictionaries.brokers.RxDicBrokers;
import com.fixterminal.shared.dictionaries.instruments.RxDicInstruments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Configuration
public class RxDictionaries {

    @Bean
    public RxDicBrokers getDicBrokers() throws ParserConfigurationException, IOException, SAXException {
        try {

            return RxDictionaryFactory.initDicBrokers();
        }
//throws ParserConfigurationException, IOException, SAXException {
//
     catch (Exception e){
            System.out.println(e);
            throw e;
        }
    }


    @Bean
    public RxDicInstruments getDicInstruments() throws ParserConfigurationException, IOException, SAXException
    {
        try {

            return RxDictionaryFactory.initDicInstruments();
        }
        catch (Exception e){
            System.out.println(e);
            throw  e;
        }
    }

}
