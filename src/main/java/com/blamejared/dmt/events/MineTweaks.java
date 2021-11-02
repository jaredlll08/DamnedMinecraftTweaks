package com.blamejared.dmt.events;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.player_skills.ingredient.data.CraftingProcessData;
import com.robertx22.age_of_exile.player_skills.ingredient.data.IngredientData;
import com.robertx22.age_of_exile.player_skills.items.TieredItem;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.registry.Database;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class MineTweaks {
    public MineTweaks() {
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onTooltipEvent(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<ITextComponent> tooltip = event.getToolTip();

        boolean addCurrencyTooltip = stack.getItem() instanceof ICurrencyItemEffect;

        PlayerEntity player = event.getPlayer();

        try {

            CraftingProcessData pdata = StackSaving.CRAFT_PROCESS.loadFrom(stack);

            if (pdata != null) {
                pdata.makeTooltip(stack, tooltip);
                return;
            }

            if (StackSaving.INGREDIENTS.has(stack)) {
                IngredientData data = StackSaving.INGREDIENTS.loadFrom(stack);
                if (data != null) {
                    data.makeTooltip(tooltip);
                    return;
                }
            }

            if (stack.getItem() instanceof TieredItem) {
                TieredItem tier = (TieredItem) stack.getItem();

                tooltip.add(new StringTextComponent("Tier " + tier.tier.getDisplayTierNumber()).withStyle(TextFormatting.LIGHT_PURPLE));
            }

            if (Screen.hasControlDown()) {
                GearItemData gear = Gear.Load(stack);
                if (gear != null) {
                    return;
                }
            }

            if (player == null || player.level == null) {
                return;
            }

            EntityData unitData = Load.Unit(player);

            if (unitData == null) {
                return;
            }

            Unit unit = unitData.getUnit();

            if (unit == null) {
                return;
            }
            if (!Database.areDatapacksLoaded(player.level)) {
                return;
            }

            com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext ctx = new com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext(stack, tooltip, unitData);

            boolean hasdata = false;

            if (stack.hasTag()) {

                ICommonDataItem data = ICommonDataItem.load(stack);

                if (data != null) {
                    data.BuildTooltip(ctx);
                    hasdata = true;
                }

                IFormattableTextComponent broken = TooltipUtils.itemBrokenText(stack, data);
                if (broken != null) {
                    tooltip.add(broken);
                }

            }

            if (!hasdata) {

                GearSlot slot = GearSlot.getSlotOf(stack.getItem());

                if (slot != null) {
                    tooltip.add(TooltipUtils.gearSlot(slot));
                }
            }

            if (addCurrencyTooltip) {
                ICurrencyItemEffect currency = (ICurrencyItemEffect) stack
                        .getItem();
                currency.addToTooltip(tooltip);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
