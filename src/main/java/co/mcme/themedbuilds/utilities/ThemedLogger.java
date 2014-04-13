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
import lombok.Getter;

public class ThemedLogger {

    @Getter
    private static final java.util.logging.Logger log = ThemedBuildPlugin.getServerInstance().getLogger();

    public static void info(String msg) {
        log.info("[TB] " + msg);
    }

    public static void warning(String msg) {
        log.warning("[TB] " + msg);
    }

    public static void severe(String msg) {
        log.severe("[TB] " + msg);
    }
}
