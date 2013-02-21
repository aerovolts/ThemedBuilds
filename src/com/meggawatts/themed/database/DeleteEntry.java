package com.meggawatts.themed.database;

import com.meggawatts.themed.database.entry.TBEntry;
import com.meggawatts.themed.util.Config;
import com.meggawatts.themed.util.Util;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeleteEntry implements Runnable {

    private final List<TBEntry> list = new ArrayList<TBEntry>();

    public DeleteEntry(TBEntry entry) {
        list.add(entry);
    }

    public void run() {
        JDCConnection conn = null;
        try {
            conn = DataManager.getConnection();
            for (TBEntry entry : list) {
                int optional = 0;
                if (entry.getSupplemental()){
                    optional = 1;
                }
                conn.createStatement().executeUpdate("DELETE FROM `" + Config.DbTBTable + "` WHERE `lot`='" + entry.getLot() + "' AND `build`='" + entry.getBuild() + "' AND `optional`=" + optional);
            }
        } catch (SQLException ex) {
            Util.severe("Unable to delete data entries from MySQL database: " + ex);
        } finally {
            conn.close();
        }
    }
}