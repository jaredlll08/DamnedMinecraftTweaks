package com.blamejared.dmt.events;

import com.blamejared.dmt.network.PacketHandler;
import com.blamejared.dmt.network.messages.MessageDifficultyLevel;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class AAClientEventHandler {
    public static BlockPos spawn;
    public static int minLevel;
    public static int maxLevel;
    public static int minLevelArea;
    public static int mobLevelPerDistance;
    public static double coordinateScale;

    public static int mapOffsetX;
    public static int mapOffsetY;
    public static int atlasStartX;
    public static int atlasStartZ;
    public static int atlasTileScale;
    public static double atlasMapScale;

    public static int getColor(int tileX, int tileZ) {
        BlockPos pos = new BlockPos(atlasStartX * 16 + (tileX * atlasTileScale * 8), 64, atlasStartZ * 16 + (tileZ * atlasTileScale * 8));

        if(spawn == null) {
            PacketHandler.CHANNEL.sendToServer(new MessageDifficultyLevel.Request());
            return ColorHelper.PackedColor.color(0, 0, 0, 0);
        }

        int level;
        double distance = spawn.distManhattan(pos);
        double scale = MathHelper.clamp(coordinateScale / 3F, 1, Integer.MAX_VALUE);
        distance *= scale;
        if (distance < minLevelArea) {
            level = minLevel;
        } else {
            int lvl = (int)(minLevel + (distance - minLevelArea) / mobLevelPerDistance);
            level = MathHelper.clamp(lvl, minLevel, maxLevel);
        }

        return ColorHelper.PackedColor.color(255 * level / maxLevel, 255, 0, 0);
    }
}
