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
package co.mcme.themedbuilds.database;

import co.mcme.themedbuilds.ThemedBuildPlugin;
import co.mcme.themedbuilds.utilities.ThemedLogger;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.Vector2D;
import com.sk89q.worldedit.regions.CuboidRegion;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.codehaus.jackson.annotate.JsonIgnore;

public class Lot {

    @Getter
    @Setter
    private ObjectId _id;
    @Getter
    @Setter
    private OfflinePlayer owner;
    @Getter
    @Setter
    @JsonIgnore
    private CuboidRegion totalLotBounds;
    @Getter
    @Setter
    @JsonIgnore
    private CuboidRegion generateBounds;
    @Getter
    @Setter
    private int size;
    @Getter
    @Setter
    //Northwest corner
    private Corner corner;
    @Getter
    @Setter
    private boolean isLotGenerated;

    public Lot() {

    }

    public Lot(OfflinePlayer owner, int size, Corner corner) {
        this._id = new ObjectId();
        this.owner = owner;
        this.size = size;
        this.corner = corner;
        this.isLotGenerated = false;
        generateBounds();
        generateDefaultLotTerrain(false);
    }

    public void generateBounds() {
        Vector pos1 = new Vector(corner.getX(), 0, corner.getZ());
        Vector pos2 = new Vector(corner.getX() + size, ThemedBuildPlugin.getTbWorld().getMaxHeight(), corner.getZ() + size);
        totalLotBounds = new CuboidRegion(pos1, pos2);
        pos1 = new Vector(corner.getX(), 0, corner.getZ());
        pos2 = new Vector(corner.getX() + size, 50, corner.getZ() + size);
        generateBounds = new CuboidRegion(pos1, pos2);
    }

    public void generateDefaultLotTerrain(boolean force) {
        if (!isLotGenerated || force) {
            for (BlockVector vec : totalLotBounds) {
                ThemedBuildPlugin.getTbWorld().getBlockAt(vec.getBlockX(), vec.getBlockY(), vec.getBlockZ()).setType(Material.AIR);
            }
            for (BlockVector vec : generateBounds) {
                ThemedBuildPlugin.getTbWorld().getBlockAt(vec.getBlockX(), vec.getBlockY(), vec.getBlockZ()).setType(Material.DIRT);
            }
            for (Vector2D vec : generateBounds.asFlatRegion()) {
                ThemedBuildPlugin.getTbWorld().getBlockAt(vec.getBlockX(), generateBounds.getMaximumY(), vec.getBlockZ()).setType(Material.GRASS);
            }
            for (BlockVector vec : generateBounds.getWalls()) {
                ThemedBuildPlugin.getTbWorld().getBlockAt(vec.getBlockX(), vec.getBlockY(), vec.getBlockZ()).setType(Material.BRICK);
            }
            for (Vector2D vec : generateBounds.asFlatRegion()) {
                ThemedBuildPlugin.getTbWorld().getBlockAt(vec.getBlockX(), generateBounds.getMinimumY(), vec.getBlockZ()).setType(Material.BEDROCK);
            }
            Vector vec = generateBounds.getCenter();
            int lotnumber = (int) Math.floor(corner.getX() / size) + 1;
            Block signBlock = ThemedBuildPlugin.getTbWorld().getBlockAt(vec.getBlockX(), generateBounds.getMaximumY() + 2, vec.getBlockZ());
            ThemedBuildPlugin.getTbWorld().getBlockAt(vec.getBlockX(), generateBounds.getMaximumY() + 1, vec.getBlockZ()).setType(Material.DIAMOND_BLOCK);
            signBlock.setType(Material.SIGN_POST);
            BlockState signState = signBlock.getState();

            if (signState instanceof Sign) {
                Sign sign = (Sign) signState;
                sign.setLine(0, ChatColor.AQUA + "Lot Number:");
                sign.setLine(1, ChatColor.DARK_BLUE + "" + ChatColor.BOLD + lotnumber);
                sign.update(true);
            }
            ThemedBuildPlugin.getTbWorld().getBlockAt(vec.getBlockX(), generateBounds.getMaximumY(), vec.getBlockZ()).setType(Material.GLOWSTONE);
            isLotGenerated = true;
        }
    }

    @Override
    public String toString() {
        try {
            return ThemedBuildPlugin.getJsonMapper().writeValueAsString(this);
        } catch (IOException ex) {
            ThemedLogger.severe(ex.getMessage());
        }
        return null;
    }

    public DBObject toDBObject() {
        return (DBObject) JSON.parse(toString());
    }
}
