package com.meggawatts.themed;

import com.meggawatts.themed.database.DataManager;
import com.meggawatts.themed.util.Config;
import com.meggawatts.themed.util.SignUtils;
import com.meggawatts.themed.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.Vector;

public class ThemedBuilds extends JavaPlugin implements Listener {

    public static String CurrentBuild;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        setupConfig();
        PluginManager pm = getServer().getPluginManager();
        try {
            getServer().getScheduler().runTaskTimerAsynchronously(this, new DataManager(this), Config.LogDelay * 20, Config.LogDelay * 20);
        } catch (Exception e) {
            Util.severe("Error initiating ThemedBuild database connection, disabling plugin");
            pm.disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        DataManager.close();
    }

    public void setupConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        Config.loadConfiguration();
        CurrentBuild = getConfig().getString("builds.current.name");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSignChange(SignChangeEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        Sign s = SignUtils.signFromBlock(block);
        BlockFace signData = SignUtils.signFacing(s);
        if (event.getLine(0).toUpperCase().contains("[THEMEDBUILD]")) {
            event.setLine(0, "§c[ThemedBuild]");
            event.setLine(1, "§b" + CurrentBuild);
            event.setLine(2, "§a" + player.getName());
            event.setLine(3, "");
            storeBuild(block, player, "add", signData.getModZ());
            player.sendMessage(ChatColor.BLUE + "Themed Build Lot Successfully claimed!");
            s.update();
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChannelRegister(PlayerRegisterChannelEvent event) {
        Util.info("Player: " + event.getPlayer().getName() + " registerend to plugin channel: " + event.getChannel());

    }

    public void storeBuild(Block block, Player p, String method, int z) {
        // detecting main from supplement, 
        // get MA1 x, check block x against ma1 x -2
        // if less, supplement, else main
        int ma1x = getConfig().getVector("builds.current.main.a1").getBlockX();
        if (block.getX() > ma1x + 2) {
            handleSupplement(block, p, method, z);
        } else {
            handleMain(block, p, method, z);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        //Identify the player
        final Player player = sender.getServer().getPlayer(sender.getName());
        if (cmd.getName().equalsIgnoreCase("tb")) {
            //What to do when a player types /pvp
            if (args.length != 0) {
                String method = args[0];
                //LOCK
                if (method.equalsIgnoreCase("create")) {
                    if (player.hasPermission("themed.admin.create")) {
                        if (args.length > 1) {
                            if (args[1] != null && args[1].length() < 15) {
                                createTB(args[1], player);
                                player.sendMessage(ChatColor.GREEN + "Successfully created new themed build with name `" + args[1] + "`");
                                return true;
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "You need to provide a name for the themed-build that is at most 14 characters");
                        }
                    }
                }
            }
        }
        return false;
    }

    public void createTB(String name, Player p) {
        Location p_loc = p.getLocation();
        setupBuild(p_loc, name);
    }

    public void setupBuild(Location loc, String name) {
        CurrentBuild = name;
        getConfig().set("builds.current.name", name);
        saveConfig();
        World world = loc.getWorld();
        Block ma1 = loc.getBlock().getRelative(BlockFace.WEST);
        Block mb1 = new Location(world, ma1.getX(), ma1.getY(), ma1.getZ() + 6).getBlock();
        ma1.setType(Material.DIAMOND_BLOCK);
        mb1.setType(Material.DIAMOND_BLOCK);
        getConfig().set("builds.current.main.a1", ma1.getLocation().toVector());
        saveConfig();
        getConfig().set("builds.current.main.b1", mb1.getLocation().toVector());
        saveConfig();
        // Main TB done, begin supplemental TB
        Block sa1 = new Location(world, ma1.getX() + 8, ma1.getY(), ma1.getZ()).getBlock(); // ma1x + 8, ma1y, ma1z
        Block sb1 = new Location(world, ma1.getX() + 8, ma1.getY(), ma1.getZ() + 6).getBlock(); //sa1 add 6 to z
        sa1.setType(Material.DIAMOND_BLOCK);
        sb1.setType(Material.DIAMOND_BLOCK);
        getConfig().set("builds.current.supplement.a1", ma1.getLocation().toVector());
        saveConfig();
        getConfig().set("builds.current.supplement.b1", mb1.getLocation().toVector());
        saveConfig();
    }

    public void handleSupplement(Block b, Player p, String m, int z_offset) {
            Sign sign = SignUtils.signFromBlock(b);
            org.bukkit.material.Sign s = (org.bukkit.material.Sign) b.getState().getData();
            b = b.getRelative(s.getAttachedFace());
            int x = b.getX();
            int z = b.getZ() + z_offset;
            Vector ma1_vec = getConfig().getVector("builds.current.main.a1");
            Vector mb1_vec = getConfig().getVector("builds.current.main.b1");
            int startx = ma1_vec.getBlockX();
            int b_z = mb1_vec.getBlockZ();
            int spacing = 24;
            String lotside;
            int lotnumber = ((x - startx) / spacing) + 1;
            if (z < b_z - 2) {
                lotside = "A";
                Util.debug("Lot side: A");
            } else {
                lotside = "B";
                Util.debug("Lot side: B");
            }
            String lot = lotside + Integer.toString(lotnumber);
            if (m.equals("add")) {
                Store.ADDSUPPLEMENTAL(lot, p);
                Util.debug("Player `" + p.getName() + "` claimed lot `" + lot + "` for themedbuild `" + CurrentBuild + "` on the supplemental side");
                sign.setLine(3, lot);
            } if (m.equals("remove")){
                String build = sign.getLine(1).replace("§b", "");
                Store.REMOVESUPPLEMENTAL(lot, p, build);
                Util.debug("Player `" + p.getName() + "` unclaimed lot `" + lot + "` for themedbuild `" + build + "` on the supplemental side");
            }
            sign.update(true);
    }

    public void handleMain(Block b, Player p, String m, int z_offset) {
            Sign sign = SignUtils.signFromBlock(b);
            org.bukkit.material.Sign s = (org.bukkit.material.Sign) b.getState().getData();
            b = b.getRelative(s.getAttachedFace());
            int x = b.getX();
            int z = b.getZ() + z_offset;
            Vector ma1_vec = getConfig().getVector("builds.current.main.a1");
            Vector mb1_vec = getConfig().getVector("builds.current.main.b1");
            int startx = ma1_vec.getBlockX();
            int b_z = mb1_vec.getBlockZ();
            int spacing = -49;
            int lotnumber = ((x - startx) / spacing) + 1;
            String lotside;
            if (z < b_z - 2) {
                lotside = "A";
                Util.debug("Lot side: A");
            } else {
                lotside = "B";
                Util.debug("Lot side: B");
            }
            String lot = lotside + Integer.toString(lotnumber);
            if (m.equals("add")) {
                Store.ADDMAIN(lot, p);
                Util.debug("Player `" + p.getName() + "` claimed lot `" + lot + "` for themedbuild `" + CurrentBuild + "` on the main side");
                sign.setLine(3, lot);
            } if (m.equals("remove")){
                String build = sign.getLine(1).replace("§b", "");
                Store.REMOVEMAIN(lot, p, build);
                Util.debug("Player `" + p.getName() + "` unclaimed lot `" + lot + "` for themedbuild `" + build + "` on the main side");
            }
            sign.update(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Block b = event.getBlock();
        Player p = event.getPlayer();
        if (b.getType().equals(Material.WALL_SIGN)) {
            // We have sign
            Sign s = SignUtils.signFromBlock(b);
            Util.debug(s.getLine(0));
            Util.debug(s.getLine(1));
            Util.debug(s.getLine(2));
            Util.debug(s.getLine(3));
            if (s.getLine(0).equals("§c[ThemedBuild]")) {
                // We have tb sign
                if (!p.hasPermission("themed.admin.break")) {
                    event.setCancelled(true);
                    p.sendMessage(ChatColor.RED + "You cannot unclaim lots!");
                }else {
                    BlockFace signData = SignUtils.signFacing(s);
                    storeBuild(b, p, "remove", signData.getModZ());
                    p.sendMessage(ChatColor.GREEN + "Successfully unclaimed lot!");
                }
            }
        }
    }

    @EventHandler
    public void onFall(BlockPhysicsEvent event) {
        Block b = event.getBlock();
        if (b.getType().equals(Material.WALL_SIGN)) {
            Sign s = SignUtils.signFromBlock(b);
            if (s.getLine(0).equals("§c[ThemedBuild]")) {
                event.setCancelled(true);
            }
        }
    }
}