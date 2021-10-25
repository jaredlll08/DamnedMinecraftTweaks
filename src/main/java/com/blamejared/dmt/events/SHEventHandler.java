package com.blamejared.dmt.events;

import com.blamejared.dmt.network.PacketHandler;
import com.blamejared.dmt.network.messages.MessagePlayerDifficulty;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.NetworkDirection;
import net.silentchaos512.scalinghealth.capability.DifficultyAffectedCapability;
import net.silentchaos512.scalinghealth.capability.DifficultySourceCapability;
import net.silentchaos512.scalinghealth.client.ClientHandler;
import net.silentchaos512.scalinghealth.utils.config.SHDifficulty;

import java.util.List;

public class SHEventHandler {
    
    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        World level = entity.level;
        if(level.isClientSide) {
            if(entity.getUUID().equals(Minecraft.getInstance().player.getUUID())) {
                SHClientEventHandler.DIFFICULTY_MAP.put(entity.getUUID(), ClientHandler.playerDifficulty);
            }
            return;
        }
        if(entity.tickCount % 20 != 0) {
            return;
        }
        List<PlayerEntity> entities = level.getEntities(EntityType.PLAYER, entity.getBoundingBox().inflate(64), ent -> !ent.getUUID().equals(entity.getUUID()));
        entities.forEach(playerEntity -> sendUpdatePacket(entity, playerEntity));
    }
    
    static void sendUpdatePacket(LivingEntity infoEntity, PlayerEntity target) {
        float difficulty = 0;
        if(infoEntity.getCapability(DifficultyAffectedCapability.INSTANCE).isPresent()) {
            difficulty = SHDifficulty.affected(infoEntity).getDifficulty();
        } else if(infoEntity.getCapability(DifficultySourceCapability.INSTANCE).isPresent()) {
            difficulty = SHDifficulty.source(infoEntity).getDifficulty();
        } else {
            // can't get a value?
            return;
        }
        MessagePlayerDifficulty messagePlayerDifficulty = new MessagePlayerDifficulty(infoEntity.getUUID(), difficulty);
        ServerPlayerEntity targetMP = (ServerPlayerEntity) target;
        PacketHandler.CHANNEL.sendTo(messagePlayerDifficulty, targetMP.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
}
