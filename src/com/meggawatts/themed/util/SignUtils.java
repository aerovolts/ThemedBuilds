package com.meggawatts.themed.util;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
 
public final class SignUtils {
        public static BlockFace signFacing(Sign sign) {
                org.bukkit.material.Sign signData = (org.bukkit.material.Sign) sign.getData();
                return signData.getFacing();
        }
       
        public static Sign signFromBlock(Block block) {
                return (Sign) block.getState();
        }
}