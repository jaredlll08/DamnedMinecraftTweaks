package com.blamejared.dmt.mixin;

import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulItem;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import javax.annotation.Nonnull;

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
}
