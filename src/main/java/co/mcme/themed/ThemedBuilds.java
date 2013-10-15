package co.mcme.themed;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ThemedBuilds extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        setupConfig();
        registerEvents();
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    public void setupConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        return false;
    }
}