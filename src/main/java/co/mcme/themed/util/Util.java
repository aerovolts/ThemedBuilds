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
package co.mcme.themed.util;

import java.util.logging.Logger;

public class Util {

    private static final Logger log = Logger.getLogger("Minecraft");

    public static void info(String msg) {
        log.info("[ThemedBuilds] " + msg);
    }

    public static void warning(String msg) {
        log.warning("[ThemedBuilds] " + msg);
    }

    public static void severe(String msg) {
        log.severe("[ThemedBuilds] " + msg);
    }

    public static void debug(String msg) {
        log.info("DEBUG: " + msg);
    }
}
