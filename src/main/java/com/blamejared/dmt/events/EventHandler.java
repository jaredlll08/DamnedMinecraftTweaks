package com.blamejared.dmt.events;

import com.blamejared.dmt.DamnedMinecraftTweaks;
import com.blamejared.dmt.damage.ElementalDamageSource;
import com.blamejared.dmt.item.ItemOintment;
import com.ma.api.events.SpellCastEvent;
import com.ma.api.spells.attributes.Attribute;
import com.ma.api.spells.base.ISpellDefinition;
import com.ma.spells.crafting.SpellRecipe;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.client.Minecraft;
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
    public void damageEvent(final LivingHurtEvent event){
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
                    System.out.println("Event fired");
                }
            }
        }
    }
    @SubscribeEvent
    public void spellCast(final SpellCastEvent event){
        ISpellDefinition recipe =  event.getSpell();
        recipe.iterateComponents((c) -> c.getContainedAttributes().forEach((attr) -> {
            if (attr == Attribute.DAMAGE) {
                c.setMultiplier(attr, c.getMultiplier(Attribute.DAMAGE) + (Load.Unit(Minecraft.getInstance().player).getUnit().getCalculatedStat(ExileDB.Stats().get(AllAttributes.INT_ID)).getValue() / 10));
            }
        }));
    }

    public static Elements getElement(String s){
        if (s != "NONE"){
            return Elements.valueOf(s);
        }
        return null;
    }
}
