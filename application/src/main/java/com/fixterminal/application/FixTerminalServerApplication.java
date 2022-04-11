package com.fixterminal.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@ComponentScan(basePackages = "com.fixterminal.app.monitors")
//@EnableAutoConfiguration
@SpringBootApplication
public class FixTerminalServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FixTerminalServerApplication.class, args);

    }
}
