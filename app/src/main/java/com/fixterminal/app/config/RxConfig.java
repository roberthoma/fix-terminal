package com.fixterminal.app.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//@Component
public class RxConfig {

    @Getter
    private static String resourcesPath = "./resources";

    @Value("${com.fixterminal.config.fixEngineConfigFileName}")
    @Getter
    private static String fixEngineConfigFileName;// = "fix_setting.cfg";

    @Getter
    private static String userConfigFileName = "user.cfg";



}
