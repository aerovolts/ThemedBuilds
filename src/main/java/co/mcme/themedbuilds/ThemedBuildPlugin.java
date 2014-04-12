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

import co.mcme.themedbuilds.database.MongoDBUtil;
import co.mcme.themedbuilds.database.Theme;
import co.mcme.themedbuilds.generator.ThemedChunkGenerator;
import co.mcme.themedbuilds.utilities.ThemedLogger;
import co.mcme.util.jackson.serialization.*;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.module.SimpleModule;

public class ThemedBuildPlugin extends JavaPlugin implements Listener {

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
    @Getter
    static World tbWorld;
    @Getter
    static Configuration pluginConfig;
    @Getter
    static MongoDBUtil mongoUtil;

    @Override
    public void onEnable() {
        pluginInstance = this;
        serverInstance = getServer();
        pluginDataFolder = pluginInstance.getDataFolder();
        setupConfig();
        try {
            mongoUtil = new MongoDBUtil(pluginConfig.getString("mongo.hostname", "localhost"), pluginConfig.getInt("mongo.port", 27017), pluginConfig.getString("mongo.username", "root"), (pluginConfig.getString("mongo.password", "")).toCharArray(), pluginConfig.getString("mongo.database", "themedbuilds"));
        } catch (UnknownHostException ex) {
            ThemedLogger.severe(ex.getMessage());
        }
        setupJackson();
        setupWorld();
        loadThemes();
        serverInstance.getPluginManager().registerEvents(this, this);
    }

    private void loadThemes() {
        DBCursor themeCursor = mongoUtil.getThemeCollection().find();
        while (themeCursor.hasNext()) {
            DBObject theme = themeCursor.next();
            ThemedLogger.info(theme.toString());
            try {
                Theme themeZ = jsonMapper.readValue(theme.toString(), Theme.class);
                ThemedLogger.info(themeZ.toString());
            } catch (IOException ex) {
                ThemedLogger.severe(ex.getMessage());
            }
        }
    }

    private void setupConfig() {
        pluginConfig = getConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void setupJackson() {
        jsonMapper = new ObjectMapper().configure(SerializationConfig.Feature.INDENT_OUTPUT, false);
        SimpleModule customSerializers = new SimpleModule("ThemedBuildsModule", new Version(1, 0, 0, null));
        customSerializers.addSerializer(ObjectId.class, new ObjectIdJsonSerializer());
        customSerializers.addDeserializer(ObjectId.class, new ObjectIdJsonDeserializer());
        customSerializers.addSerializer(OfflinePlayer.class, new PlayerJsonSerializer());
        customSerializers.addDeserializer(OfflinePlayer.class, new PlayerJsonDeserializer());
        customSerializers.addSerializer(Location.class, new LocationJsonSerializer());
        customSerializers.addDeserializer(Location.class, new LocationJsonDeserializer());
        jsonMapper.registerModule(customSerializers);
    }

    private void setupWorld() {
        World tbworld = serverInstance.getWorld("themedbuilds");
        if (tbworld == null) {
            WorldCreator creator = new WorldCreator("themedbuilds");
            creator.type(WorldType.FLAT);
            ChunkGenerator generator = new ThemedChunkGenerator();
            creator.generator(generator);
            tbWorld = creator.createWorld();
        } else {
            tbWorld = tbworld;
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer().getName().equals("meggawatts") || event.getPlayer().getName().equals("loocekibmi")) {
            event.getPlayer().teleport(new Location(tbWorld, 0, 0, 0));
        }
    }
}
