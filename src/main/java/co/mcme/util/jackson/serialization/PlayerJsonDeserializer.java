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
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class PlayerJsonDeserializer extends JsonDeserializer<OfflinePlayer> {

    @Override
    public OfflinePlayer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return Bukkit.getOfflinePlayer(UUID.fromString(jp.getText()));
    }

}
