package com.blamejared.dmt.mixin;

import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.mixin_methods.OnItemInteract;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulItem;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.SalvagedDustItem;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OnItemInteract.class)
public class SoulGearMixin {
    /**
     *
     * @author Witixin
     *
     */
    @Overwrite(aliases = "on")
    public static void on(Container screen, int i, int j, ClickType slotActionType, PlayerEntity player, CallbackInfoReturnable<ItemStack> ci){

        if (slotActionType != ClickType.PICKUP) {
            return;
        }

        ItemStack cursor = player.inventory.getCarried();

        if (!cursor.isEmpty()) {

            Slot slot = null;
            try {
                slot = screen.slots.get(i);
            } catch (Exception e) {
            }
            if (slot == null) {
                return;
            }

            ItemStack stack = slot.getItem();

            boolean success = false;

            if (stack.isDamaged() && cursor.getItem() instanceof SalvagedDustItem) {

                GearItemData gear = Gear.Load(stack);

                if (gear == null) {
                    return;
                }

                SalvagedDustItem essence = (SalvagedDustItem) cursor.getItem();

                if (essence.tier.getDisplayTierNumber() == gear.getTier()) {
                    stack.setDamageValue(stack.getDamageValue() - 250);
                    success = true;
                }
            } else if (cursor.getItem() instanceof StatSoulItem) {
                StatSoulData data = StackSaving.STAT_SOULS.loadFrom(cursor);
                GearItemData data2 = StackSaving.GEARS.loadFrom(cursor);
                if (data != null) {
                    if (data.canInsertIntoStack(stack)) {
                        //data.insertAsUnidentifiedOn(stack);
                        if (data2 == null) {
                            data.insertAsUnidentifiedOn(stack);
                        } else {
                            data2.saveToStack(stack);
                        }
                        success = true;
                    }
                }
            } else if (cursor.getItem() instanceof ICurrencyItemEffect) {
                GearItemData gear = Gear.Load(stack);
                if (gear == null) {
                    return;
                }
                LocReqContext ctx = new LocReqContext(stack, cursor);
                if (ctx.effect.canItemBeModified(ctx)) {
                    ItemStack result = ctx.effect.modifyItem(ctx).stack;

                    stack.shrink(1);
                    PlayerUtils.giveItem(result, player);

                    success = true;
                }
            }

            if (success) {

                SoundUtils.ding(player.level, player.blockPosition());
                SoundUtils.playSound(player.level, player.blockPosition(), SoundEvents.ANVIL_USE, 1, 1);

                cursor.shrink(1);
                ci.setReturnValue(ItemStack.EMPTY);
                ci.cancel();
            }

        }
    }
    }

