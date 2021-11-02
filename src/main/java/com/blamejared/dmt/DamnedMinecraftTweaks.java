package com.blamejared.dmt;

import com.blamejared.dmt.events.MineTweaks;
import com.blamejared.dmt.events.SHEventHandler;
import com.blamejared.dmt.network.PacketHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("damnedminecrafttweaks")
public class DamnedMinecraftTweaks {
    
    public DamnedMinecraftTweaks() {
        PacketHandler.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new SHEventHandler());
        MinecraftForge.EVENT_BUS.register(new MineTweaks());
    }
}
