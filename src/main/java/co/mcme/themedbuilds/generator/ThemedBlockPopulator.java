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
package co.mcme.themedbuilds.generator;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;

public class ThemedBlockPopulator extends BlockPopulator {

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        int bx = chunk.getX() << 4;
        int bz = chunk.getZ() << 4;
        for (int xx = bx; xx < bx + 16; xx++) {
            for (int zz = bz; zz < bz + 16; zz++) {
                world.getBlockAt(xx, 0, zz).setBiome(Biome.PLAINS);
            }
        }
        int centerX = (chunk.getX() << 4);
        int centerZ = (chunk.getZ() << 4);
    }

}
