package com.fixterminal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//TODO Kontrolowanie połączenia sieciowego/
//TODO Wznawianie połączenia iTP


@SpringBootApplication
public class FixTerminalServerApplication {


    public static void main(String[] args) {
        SpringApplication.run(FixTerminalServerApplication.class, args);
            System.out.println("ROHO After SpringApplication.run :)");



    }
}
