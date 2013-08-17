package co.mcme.themed;

import co.mcme.themed.database.DataManager;
import co.mcme.themed.database.entry.TBEntry;
import org.bukkit.entity.Player;

public class Store {

    public static void ADDSUPPLEMENTAL(String lot, Player p) {
        DataManager.addTBEntry(new TBEntry(p.getName(), ThemedBuilds.CurrentBuild, true, lot));
    }

    public static void ADDMAIN(String lot, Player p) {
        DataManager.addTBEntry(new TBEntry(p.getName(), ThemedBuilds.CurrentBuild, false, lot));
    }

    public static void REMOVESUPPLEMENTAL(String lot, Player p, String build) {
        DataManager.deleteTBEntry(lot, build, true);
    }

    public static void REMOVEMAIN(String lot, Player p, String build) {
        DataManager.deleteTBEntry(lot, build, false);
    }
}
