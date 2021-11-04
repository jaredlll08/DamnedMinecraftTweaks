package com.blamejared.dmt.events;

import com.blamejared.dmt.network.PacketHandler;
import com.blamejared.dmt.network.messages.MessageDifficultyLevel;
import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public class AAEventHandler {
    @SubscribeEvent
    public void onChangeWorld(PlayerEvent.PlayerChangedDimensionEvent event) {
        if(event.getPlayer() instanceof ServerPlayerEntity) {
            ServerWorld world = ((ServerPlayerEntity) event.getPlayer()).getLevel();
            DimensionConfig config = ExileDB.getDimensionConfig(world);

            PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new MessageDifficultyLevel.Return(world.getSharedSpawnPos(), config.min_lvl, config.max_lvl, config.min_lvl_area, config.mob_lvl_per_distance, world.dimensionType().coordinateScale()));
        }
    }

    @SubscribeEvent
    public void onJoinGame(PlayerEvent.PlayerLoggedInEvent event) {
        if(event.getPlayer() instanceof ServerPlayerEntity) {
            ServerWorld world = ((ServerPlayerEntity) event.getPlayer()).getLevel();
            DimensionConfig config = ExileDB.getDimensionConfig(world);

            PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new MessageDifficultyLevel.Return(world.getSharedSpawnPos(), config.min_lvl, config.max_lvl, config.min_lvl_area, config.mob_lvl_per_distance, world.dimensionType().coordinateScale()));
        }
    }
}
