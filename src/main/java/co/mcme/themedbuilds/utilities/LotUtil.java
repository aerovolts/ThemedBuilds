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
package co.mcme.themedbuilds.utilities;

import co.mcme.themedbuilds.ThemedBuildPlugin;
import co.mcme.themedbuilds.database.Corner;
import co.mcme.themedbuilds.database.Lot;
import co.mcme.themedbuilds.database.Theme;
import com.mongodb.BasicDBObject;
import java.io.IOException;

public class LotUtil {

    public Theme getThemeByExactZCoordinate(int zcoord) {
        Corner lookup = new Corner(0, zcoord);
        try {
            Theme theme = ThemedBuildPlugin.getJsonMapper().readValue(ThemedBuildPlugin.getMongoUtil().getThemeCollection().findOne(new BasicDBObject("corner", lookup.toString())).toString(), Theme.class);
            return theme;
        } catch (IOException ex) {
            ThemedLogger.severe(ex.getMessage());
        }
        return null;
    }

    public Lot getLotByExactCornerCoordinate(int xcoord, int zcoord) {
        Corner lookup = new Corner(xcoord, zcoord);
        try {
            Lot lot = ThemedBuildPlugin.getJsonMapper().readValue(ThemedBuildPlugin.getMongoUtil().getLotCollection().findOne(new BasicDBObject("corner", lookup)).toString(), Lot.class);
            return lot;
        } catch (IOException ex) {
            ThemedLogger.severe(ex.getMessage());
        }
        return null;
    }
}
