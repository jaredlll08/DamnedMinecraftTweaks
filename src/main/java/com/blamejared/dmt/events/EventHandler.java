package com.blamejared.dmt.events;

import com.blamejared.dmt.DamnedMinecraftTweaks;
import com.blamejared.dmt.damage.ElementalDamageSource;
import com.blamejared.dmt.item.ItemOintment;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class EventHandler {

    //This map handles the damage increases based on Ointment tier
    private static Map<Integer, Double> valueMap = new HashMap<>();

    public EventHandler(){
        valueMap.put(0, 0.0D);
        valueMap.put(1, 0.15);
        valueMap.put(2, 0.25);
        valueMap.put(3, 0.35);
        valueMap.put(4, 0.5);
        valueMap.put(5, 0.75);
    }

    @SubscribeEvent
    public static void damageEvent(final LivingHurtEvent event){
        if (!event.getEntityLiving().level.isClientSide){
            if (event.getSource().getDirectEntity() instanceof PlayerEntity){
                PlayerEntity player = (PlayerEntity) event.getSource().getDirectEntity();
                if (player.getMainHandItem() != null && player.getMainHandItem().getTag().contains(DamnedMinecraftTweaks.NBT_STORAGE_KEY_GLOBAL) && !(player.getMainHandItem().getItem() instanceof ItemOintment)){
                    double damage = player.getMainHandItem().getAttributeModifiers(EquipmentSlotType.MAINHAND).get(Attributes.ATTACK_DAMAGE).stream().mapToDouble(AttributeModifier::getAmount).sum();
                    int strength = player.getMainHandItem().getTagElement(DamnedMinecraftTweaks.NBT_STORAGE_KEY_GLOBAL).getInt("Strength");
                    Elements element = getElement(player.getMainHandItem().getTagElement(DamnedMinecraftTweaks.NBT_STORAGE_KEY_GLOBAL).getString("Nature"));
                    double elementalDamage = valueMap.get(strength) * damage;
                    ElementalDamageSource source = new ElementalDamageSource(element.dmgName, element);
                    source.apply(event.getEntityLiving(), elementalDamage, player, strength);
                }
            }
        }
    }


    public static Elements getElement(String s){
        if (s != "NONE"){
            return Elements.valueOf(s);
        }
        return null;
    }
}
