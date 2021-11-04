package com.blamejared.dmt.network;

import com.blamejared.dmt.events.AAClientEventHandler;
import com.blamejared.dmt.network.messages.MessageDifficultyLevel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkEvent;

public class ClientProxy extends CommonProxy {
    public ClientProxy() {
        super();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onDifficultyLevelReturn(MessageDifficultyLevel.Return msg, NetworkEvent.Context ctx) {
        AAClientEventHandler.spawn = msg.spawn;
        AAClientEventHandler.minLevel = msg.minLevel;
        AAClientEventHandler.maxLevel = msg.maxLevel;
        AAClientEventHandler.minLevelArea = msg.minLevelArea;
        AAClientEventHandler.mobLevelPerDistance = msg.mobLevelPerDistance;
        AAClientEventHandler.coordinateScale = msg.cordinateScale;
    }
}
