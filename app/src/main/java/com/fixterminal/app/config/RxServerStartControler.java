package com.fixterminal.app.config;

import com.fixterminal.app.terminal.business.RxFixTerminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//@RestController
@Controller
public class RxServerStartControler {

    @Autowired
    //RxFixTerminalMainPort terminal;
    RxFixTerminal terminal;


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
