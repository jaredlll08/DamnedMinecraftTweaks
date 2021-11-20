package com.blamejared.dmt.mixin;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearTooltipUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(value = GearTooltipUtils.class, remap = false)
public class GearTooltipUtilsMixin {
    @Inject(at = @At(value = "FIELD", target = "com/robertx22/age_of_exile/saveclasses/item_classes/GearItemData.up:Lcom/robertx22/age_of_exile/saveclasses/gearitem/gear_parts/UpgradeData;"), method = "BuildTooltip", locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void onGetDisplayName(GearItemData gear, ItemStack stack, List<ITextComponent> tooltip, EntityData data, CallbackInfo ci, List<ITextComponent> tip, TooltipInfo info, List<IFormattableTextComponent> name) {
        IFormattableTextComponent newName = name.get(0).copy();
        newName.setStyle(newName.getStyle().withItalic(false).withBold(false));
        stack.setHoverName(newName);
    }
}
