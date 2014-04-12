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
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
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
    private long claimTime;
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

    public Lot() {

    }

    public void generateBounds() {
        Vector pos1 = new Vector(corner.getX() - size, 0, corner.getZ() - size);
        Vector pos2 = new Vector(corner.getX() + size, 0, corner.getZ() + size);
        totalLotBounds = new CuboidRegion(pos1, pos2);
        pos1 = new Vector(corner.getX() - size, 0, corner.getZ() - size);
        pos2 = new Vector(corner.getX() + size, 30, corner.getZ() + size);
        generateBounds = new CuboidRegion(pos1, pos2);
        for (BlockVector vec : generateBounds) {
            ThemedBuildPlugin.getTbWorld().getBlockAt(vec.getBlockX(), vec.getBlockY(), vec.getBlockZ()).setType(Material.DIRT);
        }
        for (BlockVector vec : generateBounds.getWalls()) {
            ThemedBuildPlugin.getTbWorld().getBlockAt(vec.getBlockX(), vec.getBlockY(), vec.getBlockZ()).setType(Material.BRICK);
        }
    }
}
