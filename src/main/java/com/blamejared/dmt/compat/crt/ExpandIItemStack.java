package com.blamejared.dmt.compat.crt;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulItem;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("crafttweaker.api.item.IItemStack")
public class ExpandIItemStack {
    @ZenCodeType.Method
    public static void identifySoul(IItemStack soul) {
        if (soul.getInternal().getItem() instanceof StatSoulItem){
            StatSoulData data = StackSaving.STAT_SOULS.loadFrom(soul.getInternal());

            if (data != null) {
                data.createGearData().saveToStack(soul.getInternal());
            }
        }
    }
}