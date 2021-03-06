/*  This file is part of ThemedBuilds.
 * 
 *  ThemedBuilds is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  ThemedBuilds is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with ThemedBuilds.  If not, see <http://www.gnu.org/licenses/>.
 */
package co.mcme.themedbuilds.listeners;

import co.mcme.themedbuilds.ThemedBuildPlugin;
import co.mcme.themedbuilds.database.Corner;
import co.mcme.themedbuilds.database.Lot;
import co.mcme.themedbuilds.database.Theme;
import co.mcme.themedbuilds.utilities.DatabaseUtil;
import co.mcme.themedbuilds.utilities.ThemedLogger;
import com.sk89q.worldedit.Vector;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class LotProtectionListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (event.getPlayer().hasPermission("themedbuilds.ignoreprotection")) {
            return;
        }
        Theme currtheme = ThemedBuildPlugin.getCurrentTheme();
        Block b = event.getBlock();
        ThemedLogger.info(currtheme.getBounds().toString());
        if (currtheme.getBounds().contains(new Vector(b.getX(), b.getY(), b.getZ()))) {
            int lotindex = (int) Math.floor(b.getX() / (currtheme.getLotsize() + 6));
            int cornerz = lotindex * currtheme.getLotsize() + 6;
            if (lotindex == 0) {
                cornerz = 0;
            }
            Corner corner = new Corner(currtheme.getCorner().getX(), cornerz);
            Lot lot = DatabaseUtil.getLotByCorner(corner);
            if (lot.getOwner().equals(event.getPlayer())) {
            } else {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "You are not allowed to build in " + lot.getOwner().getName() + "'s lot");
            }
        } else {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "You are not in the build area for the current themedbuild");
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (event.getPlayer().hasPermission("themedbuilds.ignoreprotection")) {
            return;
        }
        Theme currtheme = ThemedBuildPlugin.getCurrentTheme();
        Block b = event.getBlock();
        ThemedLogger.info(currtheme.getBounds().toString());
        if (currtheme.getBounds().contains(new Vector(b.getX(), b.getY(), b.getZ()))) {
            int lotindex = (int) Math.floor(b.getX() / (currtheme.getLotsize() + 6));
            int cornerz = lotindex * currtheme.getLotsize() + 6;
            if (lotindex == 0) {
                cornerz = 0;
            }
            Corner corner = new Corner(currtheme.getCorner().getX(), cornerz);
            Lot lot = DatabaseUtil.getLotByCorner(corner);
            if (lot.getOwner().equals(event.getPlayer())) {
            } else {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "You are not allowed to build in " + lot.getOwner().getName() + "'s lot");
            }
        } else {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "You are not in the build area for the current themedbuild");
        }
    }
}
