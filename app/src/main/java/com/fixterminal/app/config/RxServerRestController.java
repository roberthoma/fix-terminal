package com.fixterminal.app.config;

import com.fixterminal.app.services.UserService;
import com.fixterminal.app.terminal.business.RxFixTerminal;
import com.fixterminal.app.terminal.business.services.RxFixHeartPrompter;
import com.fixterminal.app.terminal.business.services.RxFixTerminalPrompter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController

public class RxServerRestController {

    @Autowired
    private RxFixTerminal terminal;

    @Autowired
    private RxFixTerminalPrompter servPrompter;


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
                    response.write((servPrompter.getLog() +"\n").getBytes());
                    //  response.write((terminal.getSessionSettings() +"\n").getBytes());

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
                response.write((servPrompter.getLog() +"\n").getBytes());
               // response.write((terminal.getSessionSettings() +"\n").getBytes());

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
