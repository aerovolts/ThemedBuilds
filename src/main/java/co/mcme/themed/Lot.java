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

import org.bukkit.OfflinePlayer;
import org.bukkit.util.Vector;

public class Lot {

    private OfflinePlayer owner;
    private Vector nwBounds;
    private Vector seBounds;
    private String position;
    private Long claimtime;

    public Lot(OfflinePlayer owner, Vector northwestBounds, Vector southeastBounds, String position) {
        this.owner = owner;
        this.nwBounds = northwestBounds;
        this.seBounds = southeastBounds;
        this.position = position;
        this.claimtime = System.currentTimeMillis();
    }

    public Lot(OfflinePlayer owner, Vector northwestBounds, Vector southeastBounds, String position, Long claimTime) {
        this.owner = owner;
        this.nwBounds = northwestBounds;
        this.seBounds = southeastBounds;
        this.position = position;
        this.claimtime = claimTime;
    }

    public OfflinePlayer getOwner() {
        return owner;
    }

    public Vector getNorthWestBounds() {
        return nwBounds;
    }

    public Vector getSouthEastBounds() {
        return seBounds;
    }

    public String getPosition() {
        return position;
    }

    public Long getClaimTime() {
        return claimtime;
    }
}
