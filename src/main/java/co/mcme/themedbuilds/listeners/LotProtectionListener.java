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
import co.mcme.themedbuilds.database.Theme;
import co.mcme.themedbuilds.utilities.ThemedLogger;
import com.sk89q.worldedit.Vector;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class LotProtectionListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Theme currtheme = ThemedBuildPlugin.getCurrentTheme();
        Block b = event.getBlock();
        ThemedLogger.info(currtheme.getBounds().toString());
        if (currtheme.getBounds().contains(new Vector(b.getX(), b.getY(), b.getZ()))) {
            // In current build
        } else {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "You are not in the build area for the current themedbuild");
        }
    }
}
