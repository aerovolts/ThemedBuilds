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
package co.mcme.util.jackson.serialization;

import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class LocationJsonDeserializer extends JsonDeserializer<Location> {

    @Override
    public Location deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode root = jp.readValueAsTree();
        World world = Bukkit.getWorld(root.get("world").asText());
        double x = root.get("x").asDouble(0);
        double y = root.get("y").asDouble(0);
        double z = root.get("z").asDouble(0);
        float pitch = (float) root.get("pitch").asDouble(0);
        float yaw = (float) root.get("yaw").asDouble(0);
        return new Location(world, x, y, z, yaw, pitch);
    }

}
