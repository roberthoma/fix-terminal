package com.fixterminal.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
//
@SpringBootApplication(scanBasePackages = "com.fixterminal.*")
//@ComponentScan({
//        "com.fixterminal.terminal.businnes",
//        "com.fixterminal.terminal.adapters"
//})
@ComponentScan(basePackages = "com.fixterminal.*")
public class FixTerminalServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FixTerminalServerApplication.class, args);

    }
}
