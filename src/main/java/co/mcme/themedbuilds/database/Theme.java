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
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

public class Theme {

    @Getter
    @Setter
    private ObjectId _id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private boolean active;
    @Getter
    @Setter
    private Corner corner;
    @Getter
    @Setter
    private int lotsize;
    @Getter
    @Setter
    private int numlots;

    public Theme() {

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
}