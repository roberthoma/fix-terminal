package com.fixterminal.api.rest;

import com.fixterminal.app.commands.base.RxCommandDispatcher;
import com.fixterminal.app.commands.base.RxCommandEnum;
import com.fixterminal.app.services.RxMainServerService;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.Map;

@RestController
public class RxServerRestController {
    private static final Logger log = LoggerFactory.getLogger(RxServerRestController.class);


    @Autowired
    RxMainServerService mainService;

    @Autowired
    RxCommandDispatcher cmdDispatcher;

    private RxServerRestController(){
        log.info("Init : RxServerRestController");
    }




    @GetMapping(value="/start")
    public ResponseEntity<StreamingResponseBody> streamData() {

        try {
            mainService.serverStart();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        StreamingResponseBody responseBody = response -> {
                try {
                    response.write((mainService.getTerminalLog() +"\n").getBytes());

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
            mainService.terminalLogout();

        }
        catch (Exception e){
            e.printStackTrace();
        }

        StreamingResponseBody responseBody = response -> {
            try {
                response.write((mainService.getTerminalLog() +"\n").getBytes());

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
                response.write((mainService.getTerminal()
                                           .getSessionSettings() +"\n").getBytes());

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
               response.write((mainService.getInstrumentsList() +"\n").getBytes());
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
                response.write((mainService.getDictionaries()
                                           .getDicBrokers().toList() +"\n").getBytes());
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
                response.write((mainService.getDictionaries()
                                           .getDicInstruments().toList() +"\n").getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(responseBody);
    }
   //-----------------------------------------------------
    @GetMapping(value="/about")
    public ResponseEntity<StreamingResponseBody> getAbout() {
        StreamingResponseBody responseBody = response -> {
            try {
                response.write((mainService.getAbout() +"\n").getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(responseBody);
    }
    //-----------------------------------------------------
    //TODO Rozbudowa kontroli paramtrów
    @GetMapping(value="/command")
    public ResponseEntity<StreamingResponseBody> command  (@RequestParam String cmd ,
                                                           @RequestParam(required = false) Map<String, String> params) {

        StreamingResponseBody responseBody = response -> {
            try {
                System.out.println("cmd = "+cmd);

                if (!params.isEmpty()){
                    params.forEach((s, s2) -> System.out.println("Par="+ s +" val="+s2));
                }

                RxCommandEnum rxCmd =  cmdDispatcher.decodeCommand(cmd);
                cmdDispatcher.dispose(rxCmd);


                response.write(("WYNIK comendy cmd="+cmd +"\n").getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }

        };
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(responseBody);
    }

    //------------------------------------------------


    //-----------------------------------------------------
    String paraStr = "";
    private void setParaStr(String parSymbol , String parValue){
        paraStr = paraStr + parSymbol + " = " + parValue + "\n";
    };
    @GetMapping(value="/trade-parameters")
    public ResponseEntity<StreamingResponseBody> getTradeParams() {
        StreamingResponseBody responseBody = response -> {
            try {
                RxInstrument instr = mainService.getDictionaries().getDicInstruments().getDefault();

                mainService.getTradeParameters(instr).forEach(this::setParaStr);

                response.write((paraStr +"\n").getBytes());

            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(responseBody);
    }


}
