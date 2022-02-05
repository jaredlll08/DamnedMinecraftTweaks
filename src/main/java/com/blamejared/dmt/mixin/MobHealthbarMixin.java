package com.blamejared.dmt.mixin;

import com.minecraftserverzone.mobhealthbar.ForgeRegistryEvents;
import com.robertx22.age_of_exile.uncommon.utilityclasses.HealthUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderNameplateEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ForgeRegistryEvents.class)
public class MobHealthbarMixin {
    private static RenderNameplateEvent event;

    @Inject(at = @At("HEAD"), method = "renderHpBar", remap = false)
    private static void onRenderHpBar(RenderNameplateEvent HpBarType, CallbackInfo ci){
        event = HpBarType;
    }

    @ModifyVariable
            (
                    method = "renderHpBar(Lnet/minecraftforge/client/event/RenderNameplateEvent;)V",
                    at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/LivingEntity;getMaxHealth()F", ordinal = 0),
                    ordinal = 0
            )
    private static float dmtweaksHealthFixerupper(float original){
        return HealthUtils.getCurrentHealth(((LivingEntity)event.getEntity()));
    }
    @ModifyVariable
            (
                    method = "renderHpBar(Lnet/minecraftforge/client/event/RenderNameplateEvent;)V",
                    at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/LivingEntity;getMaxHealth()F", ordinal = 0),
                    ordinal = 1
            )
    private static float dmtweaksMaxHealthFixerupper(float original){
        return MathHelper.ceil(HealthUtils.getMaxHealth(((LivingEntity)event.getEntity())));
    }
}
