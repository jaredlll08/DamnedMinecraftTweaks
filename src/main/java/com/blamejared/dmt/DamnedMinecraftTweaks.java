package com.blamejared.dmt;

import com.blamejared.dmt.events.AAClientEventHandler;
import com.blamejared.dmt.events.AAEventHandler;
import com.blamejared.dmt.events.MineTweaks;
import com.blamejared.dmt.events.SHEventHandler;
import com.blamejared.dmt.network.ClientProxy;
import com.blamejared.dmt.network.CommonProxy;
import com.blamejared.dmt.network.PacketHandler;
import hunternif.mc.impl.atlas.client.SubTile;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.ArrayList;

@Mod("damnedminecrafttweaks")
public class DamnedMinecraftTweaks {
    public static CommonProxy PROXY;
    
    public DamnedMinecraftTweaks() {
        PacketHandler.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new SHEventHandler());
        MinecraftForge.EVENT_BUS.register(new MineTweaks());
        MinecraftForge.EVENT_BUS.register(new AAEventHandler());
    }

    private void clientSetup(final FMLClientSetupEvent evnet) {
        MinecraftForge.EVENT_BUS.register(new AAClientEventHandler());
    }
}
