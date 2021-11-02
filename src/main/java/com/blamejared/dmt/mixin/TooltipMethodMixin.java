package com.blamejared.dmt.mixin;

import com.robertx22.age_of_exile.mixin_methods.TooltipMethod;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(TooltipMethod.class)
public class TooltipMethodMixin {
    @Inject(at = @At(value = "HEAD"), method = "getTooltip", cancellable = true, remap = false)
    private static void onGetTooltip(ItemStack stack, PlayerEntity tier, ITooltipFlag gear, CallbackInfoReturnable<List<ITextComponent>> data, CallbackInfo ci) {
        ci.cancel();
    }
}
