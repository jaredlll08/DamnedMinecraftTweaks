package com.blamejared.dmt.network.messages;

import java.util.UUID;

public class MessagePlayerDifficulty {
    
    private final UUID playerUUID;
    private final float difficulty;
    
    public MessagePlayerDifficulty(UUID playerUUID, float difficulty) {
        this.playerUUID = playerUUID;
        this.difficulty = difficulty;
    }
    
    public UUID getPlayerUUID() {
        return playerUUID;
    }
    
    public float getDifficulty() {
        return difficulty;
    }
}
