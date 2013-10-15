package co.mcme.themed;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class ThemedBuilds extends JavaPlugin implements Listener {

    public static String CurrentBuild;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        setupConfig();
    }

    public void setupConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        final Player player = sender.getServer().getPlayer(sender.getName());
        if (args.length != 0) {
            String method = args[0];
            if (method.equalsIgnoreCase("create")) {
                if (player.hasPermission("themed.admin.create")) {
                }
            }
            if (method.equalsIgnoreCase("settopic")) {
                if (player.hasPermission("themed.admin.create")) {
                }
            }
        }
        return false;
    }
}