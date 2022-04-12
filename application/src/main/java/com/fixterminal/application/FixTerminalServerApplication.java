package com.fixterminal.application;

import com.fixterminal.shared.dictionaries.brokers.RxDicBrokers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FixTerminalServerApplication {


    public static void main(String[] args) {
        SpringApplication.run(FixTerminalServerApplication.class, args);
        System.out.println("ROHO After SpringApplication.run :)");



    }
}
