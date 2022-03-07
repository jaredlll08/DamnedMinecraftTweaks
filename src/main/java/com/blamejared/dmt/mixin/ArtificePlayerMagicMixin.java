package com.blamejared.dmt.mixin;

import com.ma.api.capabilities.IPlayerMagic;
import com.ma.capabilities.playerdata.magic.PlayerMagic;
import com.ma.capabilities.playerdata.magic.PlayerMagicProvider;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.packets.AllocateStatPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AllocateStatPacket.class)
public class ArtificePlayerMagicMixin  {

    @Inject(method = "onReceived", at = {@At(value = "HEAD")}, remap = false)
    private void tick(ExilePacketContext ctx, CallbackInfo ci){
        ctx.getPlayer().getCapability(PlayerMagicProvider.MAGIC).orElse((IPlayerMagic) null).getCastingResource().addRegenerationModifier("dmct_wisdom", Load.Unit(ctx.getPlayer()).getUnit().getCalculatedStat(ExileDB.Stats().get(AllAttributes.WIS_ID)).getValue() / 10);
        ctx.getPlayer().getCapability(PlayerMagicProvider.MAGIC).orElse((IPlayerMagic) null).getCastingResource().addModifier("dmct_wisdom", Load.Unit(ctx.getPlayer()).getUnit().getCalculatedStat(ExileDB.Stats().get(AllAttributes.WIS_ID)).getValue() * 30);
    }
}
