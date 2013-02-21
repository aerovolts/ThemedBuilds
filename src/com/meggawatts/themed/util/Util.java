package com.meggawatts.themed.util;

import java.util.logging.Logger;

public class Util {
    private static final Logger log = Logger.getLogger("Minecraft");
    
    public static void info(String msg){
        log.info("[ThemedBuilds] " + msg);
    }
    public static void warning(String msg){
        log.warning("[ThemedBuilds] " + msg);
    }
    public static void severe(String msg){
        log.severe("[ThemedBuilds] " + msg);
    }
    public static void debug(String msg){
        if (Config.debug){
            log.info("DEBUG: " + msg);
        }
    }
}
