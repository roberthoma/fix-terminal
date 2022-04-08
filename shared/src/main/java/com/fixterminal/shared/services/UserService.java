package com.fixterminal.shared.services;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Properties;

@Service
public class UserService {

    @Getter
    private String user;
    @Getter
    private String password;

    private void readFromCmdLine() {
        Console console = System.console();
        String user = console.readLine("user: ");
        String password = new String(console.readPassword("password: "));
    }

    public void readFromFile(){
        Properties prop = new Properties();


        String fileName =  "app-config/user.cfg";
        InputStream is = null;
        try {
            is = new FileInputStream(fileName);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        try {
            prop.load(is);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        user =  prop.getProperty("user");
        password =  prop.getProperty("password");

    }

}
