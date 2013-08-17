package co.mcme.themed.database;

import co.mcme.themed.util.Util;
import co.mcme.themed.database.entry.TBEntry;
import co.mcme.themed.util.Config;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import org.bukkit.plugin.Plugin;

public class DataManager extends TimerTask {

    private static final LinkedBlockingQueue<TBEntry> TBqueue = new LinkedBlockingQueue<TBEntry>();
    private static ConnectionManager connections;
    public static final HashMap<String, Integer> dbPlayers = new HashMap<String, Integer>();
    public static Timer loggingTimer = null;

    public DataManager(Plugin instance) throws Exception {
        connections = new ConnectionManager(Config.DbUrl, Config.DbUser, Config.DbPass);
        getConnection().close();

        //Check tables and update player list
        if (!checkTables()) {
            throw new Exception();
        }
        if (!updateDbLists()) {
            throw new Exception();
        }
        //Start logging timer
        loggingTimer = new Timer();
        loggingTimer.scheduleAtFixedRate(this, 2000, 2000);
    }

    public static void close() {
        connections.close();
        if (loggingTimer != null) {
            loggingTimer.cancel();
        }
    }

    public static void addTBEntry(TBEntry entry) {
        TBqueue.add(entry);
    }
    
    public static void deleteTBEntry(String lot, String build, boolean optional) {
		Thread thread = new Thread(new DeleteEntry(new TBEntry("null", build, optional, lot)));
		thread.start();
	}

    public static String getPlayer(int id) {
        for (Entry<String, Integer> entry : dbPlayers.entrySet()) {
            if (entry.getValue() == id) {
                return entry.getKey();
            }

        }

        return null;
    }

    public static JDCConnection getConnection() {
        try {
            return connections.getConnection();
        } catch (final SQLException ex) {
            Util.severe("Error while attempting to get connection: " + ex);
            return null;
        }
    }

    public boolean addPlayer(String name) {
        JDCConnection conn = null;
        try {
            Util.debug("Attempting to add player '" + name + "' to database");
            conn = getConnection();
            String statement = "INSERT IGNORE INTO `" + Config.DbPlayerTable + "` (`player_id`, `player`) VALUES (NULL, '" + name + "');";
            conn.createStatement().execute(statement);
        } catch (SQLException ex) {
            Util.severe("Unable to add player to database: " + ex);
            return false;
        } finally {
            conn.close();
        }
        if (!updateDbLists()) {
            return false;
        }
        return true;
    }

    private boolean updateDbLists() {
        JDCConnection conn = null;
        Statement stmnt = null;
        try {
            conn = getConnection();
            stmnt = conn.createStatement();
            ResultSet res = stmnt.executeQuery("SELECT * FROM `" + Config.DbPlayerTable + "`;");
            while (res.next()) {
                dbPlayers.put(res.getString("player"), res.getInt("player_id"));
            }
        } catch (SQLException ex) {
            Util.severe("Unable to update local data lists from database: " + ex);
            return false;
        } finally {
            try {
                if (stmnt != null) {
                    stmnt.close();
                }
                conn.close();
            } catch (SQLException ex) {
                Util.severe("unable to close SQL connection: " + ex);
            }
        }
        return true;
    }

    private boolean checkTables() {
        JDCConnection conn = null;
        Statement stmnt = null;
        try {
            conn = getConnection();
            stmnt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();

            //Check if tables exist
            if (!JDBCUtil.tableExists(dbm, Config.DbPlayerTable)) {
                Util.info("table `" + Config.DbPlayerTable + "` not found, creating...");
                stmnt.execute("CREATE TABLE IF NOT EXISTS `" + Config.DbPlayerTable + "` (`player_id` int(11) NOT NULL AUTO_INCREMENT, `player` varchar(255) NOT NULL, PRIMARY KEY (`player_id`), UNIQUE KEY `player` (`player`) );");
            }
            if (!JDBCUtil.tableExists(dbm, Config.DbTBTable)) {
                Util.info("Table `" + Config.DbTBTable + "` not found, creating...");
                stmnt.execute("CREATE TABLE IF NOT EXISTS `" + Config.DbTBTable + "` (`id` int(11) NOT NULL AUTO_INCREMENT, `date` varchar(255) NOT NULL, `player_id` int(11) NOT NULL, `build` varchar(255) NOT NULL, `optional` boolean NOT NULL, `lot` varchar(255) NOT NULL, PRIMARY KEY (`id`));");
            }
        } catch (SQLException ex) {
            Util.severe("Error checking ThemedBuild tables: " + ex);
            return false;
        } finally {
            try {
                if (stmnt != null) {
                    stmnt.close();
                }
                conn.close();
            } catch (SQLException ex) {
                Util.severe("Unable to close SQL connection: " + ex);
            }
        }
        return true;
    }

    @Override
    public void run() {
        if (TBqueue.isEmpty()) {
            return;
        }
        JDCConnection conn = getConnection();
        PreparedStatement stmnt = null;
        try {
            while (!TBqueue.isEmpty()) {
                TBEntry entry = TBqueue.poll();
                    if (!dbPlayers.containsKey(entry.getPlayer()) && !addPlayer(entry.getPlayer())) {
                        Util.debug("Player '" + entry.getPlayer() + "' not found, skipping entry");
                        continue;
                    }
                    if(dbPlayers.get(entry.getPlayer()) == null){
                        Util.debug("No Player found in hashmap, skipping entry");
                        continue;
                    }if (entry.getPlayer() == null) {
                        Util.debug("No Player found in entry, skipping");
                        continue;
                    }
                    stmnt = conn.prepareStatement("INSERT into `" + Config.DbTBTable + "` (date,player_id,build,optional,lot) VALUES (?, ?, ?, ?, ?);");
                    stmnt.setString(1, entry.getDate());
                    stmnt.setInt(2, dbPlayers.get(entry.getPlayer()));
                    stmnt.setString(3, entry.getBuild());
                    stmnt.setBoolean(4, entry.getSupplemental());
                    stmnt.setString(5, entry.getLot());
                    stmnt.executeUpdate();
                    stmnt.close();
            }
        } catch (Exception ex) {
            Util.severe("Exception: " + ex);
        }
    }
}
