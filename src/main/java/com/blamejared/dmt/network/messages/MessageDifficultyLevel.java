package com.blamejared.dmt.network.messages;

import net.minecraft.util.math.BlockPos;

public class MessageDifficultyLevel {
    private MessageDifficultyLevel(){}
    /**
     * Empty packet for requesting difficulty level from server
     */
    public static class Request {}

    /**
     * Packet for returning information used to create difficulty level to client
     */
    public static class Return {
        public final BlockPos spawn;
        public final int minLevel;
        public final int maxLevel;
        public final int minLevelArea;
        public final int mobLevelPerDistance;
        public final double cordinateScale;

        public Return(BlockPos spawn, int minLevel, int maxLevel, int minLevelArea, int mobLevelPerDistance, double cordinateScale) {
            this.spawn = spawn;
            this.minLevel = minLevel;
            this.maxLevel = maxLevel;
            this.minLevelArea = minLevelArea;
            this.mobLevelPerDistance = mobLevelPerDistance;
            this.cordinateScale = cordinateScale;
        }
    }
}
