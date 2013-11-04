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
package co.mcme.themed;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.util.Vector;

public class Lot {

    @Getter
    @Setter
    private String owner;
    @Getter
    @Setter
    private Long claimed;
    @Getter
    @Setter
    private String position;
    @Getter
    @Setter
    private Bounds bounds;

    public class Bounds {

        @Getter
        @Setter
        private Vector nw;
        @Getter
        @Setter
        private Vector se;
    }
}