package com.blamejared.dmt.mixin;

import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TooltipUtils.class)
public class TooltipUtilsMixin {
    @Inject(at = @At("RETURN"), method = "gearSlot(Lcom/robertx22/age_of_exile/database/data/gear_slots/GearSlot;)Lnet/minecraft/util/text/IFormattableTextComponent;", cancellable = true, remap = false)
    private static void dmctweaks_gearSlot(GearSlot slot, CallbackInfoReturnable<IFormattableTextComponent> cir) {
        cir.setReturnValue((new StringTextComponent(new TranslationTextComponent("dmctweaks.tooltip.geartype").getString())).withStyle(TextFormatting.DARK_GRAY).append(slot.locName().withStyle(TextFormatting.GRAY)));
    }


    @Inject(at = @At("RETURN"), method = "gearTier(I)Lnet/minecraft/util/text/IFormattableTextComponent;", cancellable = true, remap = false)
    private static void dmctweaks_gearTier(int tier, CallbackInfoReturnable<IFormattableTextComponent> cir){
        cir.setReturnValue((new StringTextComponent(new TranslationTextComponent("dmctweaks.tooltip.itemtier").getString())).withStyle(TextFormatting.DARK_GRAY).append((new StringTextComponent(tier + "")).withStyle(TextFormatting.GRAY)));
    }

    @Inject(at = @At("RETURN"), method = "gearRarity(Lcom/robertx22/age_of_exile/database/data/rarities/GearRarity;)Lnet/minecraft/util/text/IFormattableTextComponent;", cancellable = true, remap = false)
    private static void dmctweaks_gearRarity(GearRarity rarity, CallbackInfoReturnable<IFormattableTextComponent> cir){
        cir.setReturnValue((new StringTextComponent(new TranslationTextComponent("dmctweaks.tooltip.rarityprefix").getString())).withStyle(TextFormatting.DARK_GRAY).append(rarity.locName().withStyle(rarity.textFormatting())));
    }

    @Inject(at = @At("RETURN"), method = "gearLevel(I)Lnet/minecraft/util/text/IFormattableTextComponent;", cancellable = true, remap = false)
    private static void dmctweaks_gearLevel(int lvl, CallbackInfoReturnable<IFormattableTextComponent> cir){
        cir.setReturnValue((new StringTextComponent(new TranslationTextComponent("dmctweaks.tooltip.levelreq").getString())).withStyle(TextFormatting.DARK_GRAY).append((new StringTextComponent(lvl + "")).withStyle(TextFormatting.YELLOW)));
    }

    @Inject(at = @At("RETURN"), method = "dragOntoGearToUse()Lnet/minecraft/util/text/IFormattableTextComponent;", cancellable = true, remap = false)
    private static void dmctweaks_dragOntoGearToUse(CallbackInfoReturnable<IFormattableTextComponent> cir){
        cir.setReturnValue((new StringTextComponent(new TranslationTextComponent("dmctweaks.tooltip.dragtouse").getString())).withStyle(TextFormatting.DARK_GRAY, TextFormatting.BOLD));
    }

}
