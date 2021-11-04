package com.blamejared.dmt.network;

import com.blamejared.dmt.DamnedMinecraftTweaks;
import com.blamejared.dmt.events.SHClientEventHandler;
import com.blamejared.dmt.network.messages.MessageDifficultyLevel;
import com.blamejared.dmt.network.messages.MessagePlayerDifficulty;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class PacketHandler {
    
    public static SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation("damnedminecrafttweaks:main"), () -> "1.0.0", "1.0.0"::equals, "1.0.0"::equals);
    
    private static int ID = 0;
    
    public static void init() {
        
        CHANNEL.registerMessage(ID++, MessagePlayerDifficulty.class, (message, packetBuffer) -> {
            packetBuffer.writeUUID(message.getPlayerUUID());
            packetBuffer.writeFloat(message.getDifficulty());
        }, packetBuffer -> new MessagePlayerDifficulty(packetBuffer.readUUID(), packetBuffer.readFloat()), (messageCopy, contextSupplier) -> andHandling(contextSupplier, () -> {
            SHClientEventHandler.DIFFICULTY_MAP.put(messageCopy.getPlayerUUID(), messageCopy.getDifficulty());
        }));

        CHANNEL.registerMessage(ID++,
                MessageDifficultyLevel.Request.class,
                (message, packetBuffer) -> {},
                packetBuffer -> new MessageDifficultyLevel.Request(),
                (messageCopy, contextSupplier) -> andHandling(contextSupplier, () -> DamnedMinecraftTweaks.PROXY.onDifficultyLevelRequest(messageCopy, contextSupplier.get()))
        );

        CHANNEL.registerMessage(ID++,
                MessageDifficultyLevel.Return.class,
                (message, packetBuffer) -> {
                    packetBuffer.writeBlockPos(message.spawn);
                    packetBuffer.writeInt(message.minLevel);
                    packetBuffer.writeInt(message.maxLevel);
                    packetBuffer.writeInt(message.minLevelArea);
                    packetBuffer.writeInt(message.mobLevelPerDistance);
                    packetBuffer.writeDouble(message.cordinateScale);
                },
                packetBuffer -> new MessageDifficultyLevel.Return(packetBuffer.readBlockPos(), packetBuffer.readInt(), packetBuffer.readInt(), packetBuffer.readInt(), packetBuffer.readInt(), packetBuffer.readDouble()),
                (messageCopy, contextSupplier) -> andHandling(contextSupplier, () -> DamnedMinecraftTweaks.PROXY.onDifficultyLevelReturn(messageCopy, contextSupplier.get()))
        );
    }
    
    private static void andHandling(final Supplier<NetworkEvent.Context> contextSupplier, final Runnable enqueuedWork) {
        
        contextSupplier.get().enqueueWork(enqueuedWork);
        contextSupplier.get().setPacketHandled(true);
    }
}
