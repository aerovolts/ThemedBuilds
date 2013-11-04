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

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

public class ThemedBuild {

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Long start;
    @Getter
    @Setter
    private lots lots;

    public class lots {

        @Getter
        @Setter
        int startx;
        @Getter
        @Setter
        int startz;
        @Getter
        @Setter
        int size;
        @Getter
        @Setter
        int count;
        @Getter
        @Setter
        ArrayList<Lotdat> collection;

        public class Lotdat {

            @Getter
            @Setter
            String position;
            @Getter
            @Setter
            String owner;
        }
    }
    @Getter
    @Setter
    private ArrayList<String> participants;
}
