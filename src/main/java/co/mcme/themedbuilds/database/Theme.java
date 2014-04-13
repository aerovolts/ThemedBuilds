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
import co.mcme.themedbuilds.utilities.DatabaseUtil;
import co.mcme.themedbuilds.utilities.ThemedLogger;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonIgnore;

public class Theme {

    @Getter
    @Setter
    private ObjectId _id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Corner corner;
    @Getter
    @Setter
    private int lotsize;
    @Getter
    @Setter
    private int numlots;
    @Getter
    @Setter
    private ArrayList<ObjectId> lotIds = new ArrayList();
    ;
    @Getter
    @Setter
    @JsonIgnore
    private ArrayList<Lot> lots = new ArrayList();

    public Theme() {

    }

    public Theme(String name, int zstart, int size) {
        this._id = new ObjectId();
        this.name = name;
        this.corner = new Corner(0, zstart);
        if ((size % 2) == 0) {
            size = size + 1;
        }
        this.lotsize = size;
    }

    @JsonIgnore
    public CuboidRegion getBounds() {
        Vector pos1 = new Vector(corner.getX(), 0, corner.getZ());
        Vector pos2 = new Vector(((corner.getX() + lotsize) * numlots) + (6 * (numlots - 1)), ThemedBuildPlugin.getTbWorld().getMaxHeight(), corner.getZ() + lotsize);
        return new CuboidRegion(pos1, pos2);
    }

    @Override
    public String toString() {
        try {
            return ThemedBuildPlugin.getJsonMapper().writeValueAsString(this);
        } catch (IOException ex) {
            ThemedLogger.getLog().log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public DBObject toDBObject() {
        return (DBObject) JSON.parse(toString());
    }

    public ArrayList<Lot> fetchLots() {
        lots.clear();
        for (ObjectId id : lotIds) {
            lots.add(DatabaseUtil.getLotById(id));
        }
        return lots;
    }
}
