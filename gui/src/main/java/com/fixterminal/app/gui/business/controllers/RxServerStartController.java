package com.fixterminal.app.gui.business.controllers;

import com.fixterminal.app.terminal.adapters.gui.RxFixTerminalMainAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//@RestController
@Controller
public class RxServerStartController {
    private static final Logger log = LoggerFactory.getLogger(RxServerStartController.class);

    @Autowired
    RxFixTerminalMainAdapter terminal;

    private RxServerStartController(){
      log.info("Init : RxServerStartController");
    }

    @GetMapping("/index")
    public String getIndex(Model model){
        model.addAttribute("heartBB", terminal.getHeartBB());
        return "index";
    }


    @GetMapping("/")
    public String getHome(Model model){
        model.addAttribute("heartBB", terminal.getHeartBB());
        return "index";
    }

    @GetMapping("/about")
    public String getAbout(Model model){
        model.addAttribute("fixEngineInfo", terminal.getFixEngineInfo());
        return "about";
    }

}
