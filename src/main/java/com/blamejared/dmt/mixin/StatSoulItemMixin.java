package com.blamejared.dmt.mixin;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulItem;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulItem.getSoul;

@Mixin(StatSoulItem.class)
abstract class StatSoulItemMixin extends Item {


    public StatSoulItemMixin(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    /**
     *
     * @author amo
     *
     */
     @Overwrite(aliases = "getName", remap = false)
     public ITextComponent getName(ItemStack stack) {
         IFormattableTextComponent txt = new TranslationTextComponent(this.getDescriptionId());

         try {
             StatSoulData data = getSoul(stack);
             if (data == null) {
                 return txt;
             } else {
                 GearRarity rar = (GearRarity) ExileDB.GearRarities().get(data.rar);
                 IFormattableTextComponent t = rar.locName();
                 t.append(" ")
                         .append(new TranslationTextComponent("dmctweaks.morphstone.name"))
                         .withStyle(rar.textFormatting());
                 return t;
             }
         } catch (Exception var7) {
             var7.printStackTrace();
             return txt;
         }
     }

    @Inject(at = @At("TAIL"), method = "appendHoverText(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/util/ITooltipFlag;)V", remap = false)
    private static void dmctweaks_appendhovertext(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag context, CallbackInfo ci){
         for (int i = 0; i < 1; i++){
             tooltip.remove(tooltip.size()-1);
         }
         tooltip.add((new TranslationTextComponent("dmctweaks.tooltip.infusesontogear")).withStyle(TextFormatting.DARK_GRAY));
         tooltip.add(TooltipUtils.dragOntoGearToUse());
    }
}
