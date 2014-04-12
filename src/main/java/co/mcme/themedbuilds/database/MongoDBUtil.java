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
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import lombok.Getter;

public class MongoDBUtil {

    @Getter
    private final MongoClient client;
    @Getter
    private final DB database;
    @Getter
    private DBCollection themeCollection;
    @Getter
    private DBCollection playerCollection;

    public MongoDBUtil(String hostname, int port, String username, char[] password, String database) throws UnknownHostException {
        client = new MongoClient(hostname, port);
        this.database = client.getDB(database);
        boolean auth = this.database.authenticate(username, password);
        if (!auth) {
            ThemedLogger.severe("Could not authenticate to '" + hostname + ":" + port + "/" + database + "' disabling plugin.");
            ThemedBuildPlugin.getServerInstance().getPluginManager().disablePlugin(ThemedBuildPlugin.getPluginInstance());
        }
        loadCollections();
    }

    private void loadCollections() {
        themeCollection = database.getCollection("themelist");
        if (themeCollection == null) {
            themeCollection = database.createCollection("themelist", null);
        }
        //playerCollection = database.getCollection("");
    }

}
