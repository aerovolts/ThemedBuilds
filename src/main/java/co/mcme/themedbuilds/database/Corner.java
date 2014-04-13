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
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;

public class Corner {

    @Getter
    @Setter
    private int x;
    @Getter
    @Setter
    private int z;

    public Corner() {

    }

    public Corner(int x, int z) {
        this.x = x;
        this.z = z;
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
