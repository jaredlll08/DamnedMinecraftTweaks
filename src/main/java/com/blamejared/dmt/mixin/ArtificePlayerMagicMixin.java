package com.blamejared.dmt.mixin;

import com.ma.capabilities.playerdata.magic.PlayerMagic;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerMagic.class)
public class ArtificePlayerMagicMixin  {

    @Inject(method = "tick", at = {@At(value = "HEAD")}, remap = false)
    private void tick(PlayerEntity regen_pct_per_tick, CallbackInfo ci){
        PlayerMagic playerMagic = (PlayerMagic) ((Object) this);
        playerMagic.getCastingResource().addModifier("dmct_wisdom", (int) Load.Unit(regen_pct_per_tick).getUnit().getCalculatedStat(ExileDB.Stats().get(AllAttributes.WIS_ID)).getValue());
        playerMagic.getCastingResource().setMaxAmountByLevel((int)Load.Unit(regen_pct_per_tick).getUnit().getCalculatedStat(ExileDB.Stats().get(AllAttributes.WIS_ID)).getValue());
    }
}
