package com.fixterminal.gui.controllers;

import com.fixterminal.app.terminal.adapters.RxFixTerminalMainPort;
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

    private RxServerRestController(){
      log.info("Init : RxServerRestController");
    }

    @Autowired
    private RxFixTerminalMainPort terminal;


    @Autowired
    UserService userService;


    @GetMapping(value="/logs")
    public ResponseEntity<StreamingResponseBody> streamData() {

        try {
            if (!terminal.isWorking()) {
                terminal.start();
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


}
