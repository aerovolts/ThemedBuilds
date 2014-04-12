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
package co.mcme.themedbuilds;

import java.io.File;
import lombok.Getter;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

public class ThemedBuildPlugin extends JavaPlugin {

    @Getter
    static Server serverInstance;
    @Getter
    static ThemedBuildPlugin pluginInstance;
    @Getter
    static File pluginDataFolder;
    @Getter
    static String fileSeperator = System.getProperty("file.separator");
    @Getter
    static ObjectMapper jsonMapper;

    @Override
    public void onEnable() {
        pluginInstance = this;
        serverInstance = getServer();
        pluginDataFolder = pluginInstance.getDataFolder();
        jsonMapper = new ObjectMapper().configure(SerializationConfig.Feature.INDENT_OUTPUT, false);
    }
}
