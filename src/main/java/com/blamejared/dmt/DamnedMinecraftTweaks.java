package com.blamejared.dmt;

import com.blamejared.dmt.events.AAClientEventHandler;
import com.blamejared.dmt.events.AAEventHandler;
import com.blamejared.dmt.events.EventHandler;
import com.blamejared.dmt.events.MSClientEventHandler;
import com.blamejared.dmt.item.ItemOintment;
import com.blamejared.dmt.network.ClientProxy;
import com.blamejared.dmt.network.CommonProxy;
import com.blamejared.dmt.network.PacketHandler;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(DamnedMinecraftTweaks.MODID)
public class DamnedMinecraftTweaks {
    public static CommonProxy PROXY;
    public static final String MODID = "damnedminecrafttweaks";
    public static final String NBT_STORAGE_KEY_GLOBAL = "DMCT";

    public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<ItemOintment> OINTMENT_ITEM = ITEM_REGISTRY.register("item_ointment", () -> new ItemOintment(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    
    public DamnedMinecraftTweaks() {
        PacketHandler.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        ITEM_REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
        PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new AAEventHandler());
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    private void clientSetup(final FMLClientSetupEvent evnet) {
        MinecraftForge.EVENT_BUS.register(new MSClientEventHandler());
        MinecraftForge.EVENT_BUS.register(new AAClientEventHandler());

    }
    public static ResourceLocation rl(String s){
        return new ResourceLocation(MODID, s);
    }
}
