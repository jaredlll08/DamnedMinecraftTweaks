package com.blamejared.dmt.mixin;

import com.lycanitesmobs.core.spawner.location.StructureSpawnLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StructureSpawnLocation.class)
public class StructureSpawnLocationMixin {
    
    @Shadow
    public int structureRange;
    
    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(CallbackInfo ci) {
        this.structureRange = 8;
    }
    
}
