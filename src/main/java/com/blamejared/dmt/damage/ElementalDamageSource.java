package com.blamejared.dmt.damage;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.ExilePotionEvent;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;

public class ElementalDamageSource extends DamageSource {

    private Elements element;

    public ElementalDamageSource(String p_i1566_1_, Elements element) {
        super(p_i1566_1_);
        this.element = element;
    }

    @Override
    public boolean isMagic() {
        return true;
    }

    public void setElement(Elements element){
        this.element = element;
    }
    public Elements getElement(){
        return this.element;
    }

    public void apply(LivingEntity entity, double damage, PlayerEntity player, int strength){
        entity.hurt(this, (float)damage);
        if (element.equals(Elements.Physical)){
                ExileEffect effect = ExileDB.ExileEffects().get("bleed");
                ExilePotionEvent potionEvent = (ExilePotionEvent) EventBuilder.ofEffect(player, entity, strength, effect, com.robertx22.age_of_exile.uncommon.effectdatas.GiveOrTake.give, 100).build();
                potionEvent.spellid = "dmct_bleed";
                potionEvent.Activate();
        }
    }

}
