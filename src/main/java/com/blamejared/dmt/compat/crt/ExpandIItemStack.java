package com.blamejared.dmt.compat.crt;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulItem;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("crafttweaker.api.item.IItemStack")
public class ExpandIItemStack {
    @ZenCodeType.Method
    public static void identifySoul(IItemStack soul) {
        if (soul.getInternal().getItem() instanceof StatSoulItem){
            StatSoulData data = StackSaving.STAT_SOULS.loadFrom(soul.getInternal());

            if (data != null && StackSaving.GEARS.loadFrom(soul.getInternal()) == null) {
                data.createGearData().saveToStack(soul.getInternal());
            }
        }
    }
}