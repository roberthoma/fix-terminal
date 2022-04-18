package com.fixterminal.gui.controllers;

import com.fixterminal.monitors_adapters.RxMonitorsDeskPort;
import com.fixterminal.shared.dictionaries.RxDictionaries;
import com.fixterminal.shared.dictionaries.brokers.RxDicBrokers;
import com.fixterminal.terminal.adapters.RxFixTerminalMainPort;
import com.fixterminal.shared.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
public class RxServerRestController {
    private static final Logger log = LoggerFactory.getLogger(RxServerRestController.class);


    @Autowired
    private RxFixTerminalMainPort terminal;

    @Autowired
    private RxMonitorsDeskPort monitorsDesk;

//    @Autowired
//    RxDicBrokers brokers;

    @Autowired
    RxDictionaries dictionaries;

    @Autowired
    UserService userService;

    private RxServerRestController(){
        log.info("Init : RxServerRestController");
    }




    @GetMapping(value="/start")
    public ResponseEntity<StreamingResponseBody> streamData() {

        try {
            if (!terminal.isWorking()) {
                terminal.start();
                monitorsDesk.startConnectionController();

                //TODO obsługa błędów logowania
                userService.readFromFile();
                terminal.logon(userService.getUser(), userService.getPassword());

            }

        }
        catch (Exception e){
            System.out.println(e);
        }

        StreamingResponseBody responseBody = response -> {
                try {
                    response.write((terminal.getLog() +"\n").getBytes());

                } catch (Exception e) {
                    e.printStackTrace();
                }
        };
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(responseBody);
    }



    @GetMapping(value="/logout")
    public ResponseEntity<StreamingResponseBody> logout() {

        try {
                terminal.logout();

        }
        catch (Exception e){
            System.out.println(e);
        }

        StreamingResponseBody responseBody = response -> {
            try {
                response.write((terminal.getLog() +"\n").getBytes());

            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(responseBody);
    }




    @GetMapping(value="/settings")
    public ResponseEntity<StreamingResponseBody> settings() {


        StreamingResponseBody responseBody = response -> {
            try {
                response.write((terminal.getSessionSettings() +"\n").getBytes());

            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(responseBody);
    }


    @GetMapping(value="/monitors")
    public ResponseEntity<StreamingResponseBody> monitors() {


        StreamingResponseBody responseBody = response -> {
            try {
               response.write((monitorsDesk.getInstrumentsList() +"\n").getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(responseBody);
    }

    @GetMapping(value="/dic-brokers")
    public ResponseEntity<StreamingResponseBody> dicBrokersList() {
        StreamingResponseBody responseBody = response -> {
            try {
                response.write((dictionaries.getDicBrokers().toList() +"\n").getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(responseBody);
    }

    @GetMapping(value="/dic-instruments")
    public ResponseEntity<StreamingResponseBody> dicInstumentsList() {
        StreamingResponseBody responseBody = response -> {
            try {
                response.write((dictionaries.getDicInstruments().toList() +"\n").getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(responseBody);
    }


}
