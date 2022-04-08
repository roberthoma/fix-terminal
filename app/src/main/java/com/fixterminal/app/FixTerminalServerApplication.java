package com.fixterminal.app;

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@Configuration
//@EntityScan(value = {
//        "com.fixterminal.app",
//        "com.fixterminal.terminal",
//        "com.fixterminal.shared",
//        "com.fixterminal.monitors"
//})
//
//@ComponentScan(value = {
//        "com.fixterminal.app",
//        "com.fixterminal.terminal",
//        "com.fixterminal.shared",
//        "com.fixterminal.monitors"
//
//},lazyInit = false )
//@SpringBootApplication

//@ComponentScan(basePackages = "com.fixterminal.app")
//@ComponentScan({
//        "com.fixterminal.app",
//        "com.fixterminal.app.terminal",
//        "com.fixterminal.app.shared",
//        "com.fixterminal.app.gui"
//
//        })
//
//@Configuration
//@SpringBootApplication
//public class FixTerminalServerApplication extends SpringBootServletInitializer {
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(FixTerminalServerApplication.class);
//    }
//
//    public static void main(String[] args) {
//        ApplicationContext applicationContext
//        new FixTerminalServerApplication()
//           .configure(new SpringApplicationBuilder(FixTerminalServerApplication.class)).run(args);
//
//        for (String name : applicationContext.getBeanDefinitionNames()) {
//            System.out.println(name);
//        }
//
//    }
//
////    public static void main(String[] args) {
////        SpringApplication.run(FixTerminalServerApplication.class, args);
////
////    }
//}


@ComponentScan("com.fixterminal")
@SpringBootApplication
public class FixTerminalServerApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext =
                SpringApplication.run(FixTerminalServerApplication.class, args);


//        for (String name : applicationContext.getBeanDefinitionNames()) {
//            System.out.println(name);
//        }
    }
}

