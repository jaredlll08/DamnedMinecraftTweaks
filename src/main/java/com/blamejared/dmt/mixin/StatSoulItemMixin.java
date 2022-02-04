package com.blamejared.dmt.mixin;

import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulItem;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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
     * @reason rewrite method body
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

     /**
      *
      * @author amo
      * @reason fixing tooltips
      *
      */
     @OnlyIn(Dist.CLIENT)
     @Overwrite(aliases = "appendHoverText", remap = false)
     public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag context) {
         StatSoulData data = (StatSoulData) StackSaving.STAT_SOULS.loadFrom(stack);
         if (data != null) {
             if (data.gear != null) {
                 data.gear.BuildTooltip(new TooltipContext(stack, tooltip, Load.Unit(ClientOnly.getPlayer())));
             } else {
                 tooltip.add(TooltipUtils.gearTier(data.tier));
                 tooltip.add(TooltipUtils.gearRarity((GearRarity)ExileDB.GearRarities().get(data.rar)));
             }
         }

         tooltip.add(new StringTextComponent(""));
         tooltip.add((new TranslationTextComponent("dmctweaks.tooltip.infusesontogear")).withStyle(TextFormatting.DARK_GRAY));
         tooltip.add(TooltipUtils.dragOntoGearToUse());
     }
}
