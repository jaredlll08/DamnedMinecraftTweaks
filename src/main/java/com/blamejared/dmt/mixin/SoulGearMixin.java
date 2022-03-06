package com.blamejared.dmt.mixin;

import com.blamejared.dmt.DamnedMinecraftTweaks;
import com.blamejared.dmt.item.ItemOintment;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.mixin_methods.OnItemInteract;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.SocketData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulItem;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ISalvagable;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.SalvagedDustItem;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
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
                        if (data2 == null) {
                            data.insertAsUnidentifiedOn(stack);
                        } else {
                            data2.saveToStack(stack);
                        }
                        success = true;
                    }
                }
            } else if (cursor.getItem() instanceof ItemOintment){
                GearItemData gear = Gear.Load(stack);
                if (gear == null) {
                    return;
                }
                stack.getOrCreateTag().put(DamnedMinecraftTweaks.NBT_STORAGE_KEY_GLOBAL, cursor.getTag());
                success = true;
            }
            else {
                ItemStack gem;
                if (cursor.getItem() instanceof ICurrencyItemEffect) {
                    LocReqContext ctx = new LocReqContext(player, stack, cursor);
                    if (ctx.effect.canItemBeModified(ctx)) {
                        gem = ctx.effect.modifyItem(ctx).stack;
                        stack.shrink(1);
                        slot.set(gem);
                        success = true;
                    }
                } else if (cursor.getItem() == SlashItems.SALVAGE_HAMMER.get()) {
                    ISalvagable data = ISalvagable.load(stack);
                    if (data == null && stack.getItem() instanceof ISalvagable) {
                        data = (ISalvagable) stack.getItem();
                    }


                    if (data != null) {
                        stack.shrink(1);
                        SoundUtils.playSound(player, SoundEvents.ANVIL_USE, 1.0F, 1.0F);
                        data.getSalvageResult(stack).forEach((x) -> {
                            PlayerUtils.giveItem(x, player);
                        });
                        ci.setReturnValue(ItemStack.EMPTY);
                        ci.cancel();
                        return;
                    }
                } else if (cursor.getItem() == SlashItems.SOCKET_EXTRACTOR.get()) {
                    GearItemData gear = Gear.Load(stack);
                    if (gear != null && gear.sockets != null && gear.sockets.sockets.size() > 0) {
                        try {
                            gem = new ItemStack(((SocketData) gear.sockets.sockets.get(0)).getGem().getItem());
                            gear.sockets.sockets.remove(0);
                            Gear.Save(stack, gear);
                            PlayerUtils.giveItem(gem, player);
                        } catch (Exception var13) {
                            var13.printStackTrace();
                        }

                        ci.setReturnValue(ItemStack.EMPTY);
                        ci.cancel();
                        return;
                    }
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

