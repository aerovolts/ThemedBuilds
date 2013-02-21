package com.meggawatts.themed.util;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;

public class Config {
    
    private static Configuration conf;

    public Config() {
        loadConfiguration();
    }
    public static boolean debug;
    // SQL variables
    public static String DbPlayerTable;
    public static String DbTBTable;
    public static String DbUser;
    public static String DbPass;
    public static String DbUrl;
    public static int LogDelay;
    
    public static void loadConfiguration() {
        conf = Bukkit.getPluginManager().getPlugin("ThemedBuilds").getConfig();
        debug = conf.getBoolean("general.debug");
        DbPlayerTable = conf.getString("sql.playertable");
        DbTBTable = conf.getString("sql.tbtable");
        DbUser = conf.getString("sql.user");
        DbPass = conf.getString("sql.password");
        DbUrl = "jdbc:mysql://" + conf.getString("sql.host") + ":" + conf.getString("sql.port") + "/" + conf.getString("sql.database");
        LogDelay = conf.getInt("sql.logdelay");
    }
}
