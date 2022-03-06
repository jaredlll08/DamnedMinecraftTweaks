package com.blamejared.dmt.item;

import com.blamejared.dmt.DamnedMinecraftTweaks;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;

public class ItemOintment extends Item {

    public ItemOintment(Properties props) {
        super(props);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        //DEBUG ONLY - TAGS SHOULD BE HANDLED BY CRAFTING
        ItemStack toReturn = context.getItemInHand();
        toReturn.getOrCreateTag().putString("Nature", Elements.Physical.name());
        toReturn.getTag().putInt("Strength", 2);
        return super.useOn(context);
    }
}
