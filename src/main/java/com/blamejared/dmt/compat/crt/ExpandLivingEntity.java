package com.blamejared.dmt.compat.crt;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.dmt.events.SHClientEventHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import net.silentchaos512.scalinghealth.capability.DifficultyAffectedCapability;
import net.silentchaos512.scalinghealth.capability.DifficultySourceCapability;
import net.silentchaos512.scalinghealth.utils.config.SHDifficulty;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("crafttweaker.api.entity.MCLivingEntity")
public class ExpandLivingEntity {
    
    @ZenCodeType.Method
    public static float getSHDifficulty(LivingEntity entity) {
        if(EffectiveSide.get().isClient()) {
            return SHClientEventHandler.DIFFICULTY_MAP.getOrDefault(entity.getUUID(), 0F);
        } else {
            float difficulty = 0;
            if(entity.getCapability(DifficultyAffectedCapability.INSTANCE).isPresent()) {
                difficulty = SHDifficulty.affected(entity).getDifficulty();
            } else if(entity.getCapability(DifficultySourceCapability.INSTANCE).isPresent()) {
                difficulty = SHDifficulty.source(entity).getDifficulty();
            }
            return difficulty;
        }
    }
}


