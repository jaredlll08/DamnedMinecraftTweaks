package com.blamejared.dmt.events;

import com.blamejared.dmt.network.PacketHandler;
import com.blamejared.dmt.network.messages.MessageDifficultyLevel;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction8;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3i;

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
    
    private static final int[][] COLOURS = new int[][] {{0, 128, 255}, {128, 255, 0}, {255, 0, 165}, {255, 255, 255}};
    
    public static int getColor(int tileX, int tileZ) {
        
        if(spawn == null) {
            PacketHandler.CHANNEL.sendToServer(new MessageDifficultyLevel.Request());
            return ColorHelper.PackedColor.color(0, 0, 0, 0);
        }
    
        BlockPos pos = new BlockPos(atlasStartX * 16 + ((tileX) * atlasTileScale * 8), 64, atlasStartZ * 16 + ((tileZ) * atlasTileScale * 8));
        int level = calculateLevel(pos);
        
        int areaLevel = 0;
        for(Direction8 value : Direction8.values()) {
            Vector3i offset = value.getDirections().stream().map(Direction::getNormal).reduce(AAClientEventHandler::addVecs).orElseThrow(() -> new ArithmeticException("Something went wrong calculating offset??"));
            pos = new BlockPos(atlasStartX * 16 + ((tileX + offset.getX()) * atlasTileScale * 8), 64, atlasStartZ * 16 + ((tileZ + offset.getZ()) * atlasTileScale * 8));
            areaLevel += calculateLevel(pos);
        }
        areaLevel /= Direction8.values().length;
        
        int alpha = (int) remap(areaLevel, minLevel, maxLevel, 70, 75);
        float blendValue;
        int firstColour;
        int secondColour;
        if(level < (maxLevel * 0.3)) {
            blendValue = remap(level, minLevel, maxLevel * 0.3f, 0,1);
            firstColour = getColour(10, COLOURS[3]);
            secondColour = getColour(alpha, COLOURS[0]);
        } else if(level < (maxLevel * 0.6)) {
            blendValue = remap(level, maxLevel * 0.3f, maxLevel * 0.6f, 0,1);
            firstColour = getColour(alpha, COLOURS[0]);
            secondColour = getColour(alpha, COLOURS[1]);
        } else {
            blendValue = remap(level, maxLevel * 0.6f, maxLevel, 0,1);
            firstColour = getColour(alpha, COLOURS[1]);
            secondColour = getColour(alpha, COLOURS[2]);
        }
        return blend(firstColour, secondColour, blendValue);
    }
    
    
    private static Vector3i addVecs(Vector3i first, Vector3i second) {
        return new Vector3i(first.getX() + second.getX(), first.getY() + second.getY(), first.getZ() + second.getZ());
    }
    
    private static int calculateLevel(BlockPos pos) {
        int level;
        double distance = spawn.distManhattan(pos);
        double scale = MathHelper.clamp(coordinateScale / 3F, 1, Integer.MAX_VALUE);
        distance *= scale;
        if(distance < minLevelArea) {
            level = minLevel;
        } else {
            int lvl = (int) (minLevel + (distance - minLevelArea) / mobLevelPerDistance);
            level = MathHelper.clamp(lvl, minLevel, maxLevel);
        }
        return level;
    }
    
    private static int getColour(int alpha, int[] rgb) {
        return ColorHelper.PackedColor.color(alpha, rgb[0], rgb[1], rgb[2]);
    }
    
    private static int blend(int color1, int color2, float ratio) {
        float ir = 1.0f - ratio;
        
        float[] rgb1 = getARGB(color2);
        float[] rgb2 = getARGB(color1);
        
        return toInt(new float[] {rgb1[0] * ratio + rgb2[0] * ir, rgb1[1] * ratio + rgb2[1] * ir, rgb1[2] * ratio + rgb2[2] * ir, rgb1[3] * ratio + rgb2[3] * ir});
    }
    
    public static int blendFullAlpha(int color1, int color2, float ratio) {
        float ir = 1.0f - ratio;
        
        float[] rgb1 = getARGB(color2);
        float[] rgb2 = getARGB(color1);
        
        return toInt(new float[] {1, rgb1[1] * ratio + rgb2[1] * ir, rgb1[2] * ratio + rgb2[2] * ir, rgb1[3] * ratio + rgb2[3] * ir});
    }
    
    private static int toInt(float[] argb) {
        int a = Math.round(argb[0] * 255) & 0xFF;
        int r = Math.round(argb[1] * 255) & 0xFF;
        int g = Math.round(argb[2] * 255) & 0xFF;
        int b = Math.round(argb[3] * 255) & 0xFF;
        return (a << 24) + (r << 16) + (g << 8) + (b);
    }
    
    // TODO use the vanilla class for this
    private static float getRed(int hex) {
        return ((hex >> 16) & 0xFF) / 255f;
    }
    
    private static float getGreen(int hex) {
        return ((hex >> 8) & 0xFF) / 255f;
    }
    
    private static float getBlue(int hex) {
        return ((hex) & 0xFF) / 255f;
    }
    
    private static float getAlpha(int hex) {
        return ((hex >> 24) & 0xff) / 255f;
    }
    
    private static float[] getARGB(int hex) {
        return new float[] {getAlpha(hex), getRed(hex), getGreen(hex), getBlue(hex)};
    }
    
    public static float remap(float value, float currentLow, float currentHigh, float newLow, float newHigh) {
        return newLow + (value - currentLow) * (newHigh - newLow) / (currentHigh - currentLow);
    }
}