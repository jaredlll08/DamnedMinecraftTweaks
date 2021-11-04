package com.blamejared.dmt.network;

import com.blamejared.dmt.network.messages.MessageDifficultyLevel;
import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public class CommonProxy {
    public CommonProxy() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(FMLCommonSetupEvent event) {
        PacketHandler.init();
    }

    public void onDifficultyLevelRequest(MessageDifficultyLevel.Request msg, NetworkEvent.Context ctx) {
        if(ctx.getSender() != null) {
            ServerWorld world = ctx.getSender().getLevel();
            DimensionConfig config = ExileDB.getDimensionConfig(world);

            PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(ctx::getSender), new MessageDifficultyLevel.Return(world.getSharedSpawnPos(), config.min_lvl, config.max_lvl, config.min_lvl_area, config.mob_lvl_per_distance, world.dimensionType().coordinateScale()));
        }
    }

    public void onDifficultyLevelReturn(MessageDifficultyLevel.Return msg, NetworkEvent.Context ctx) {}
}
