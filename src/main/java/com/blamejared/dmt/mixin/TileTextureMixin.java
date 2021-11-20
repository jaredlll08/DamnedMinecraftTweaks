package com.blamejared.dmt.mixin;

import com.blamejared.dmt.events.AAClientEventHandler;
import com.blamejared.dmt.events.MSClientEventHandler;
import com.blamejared.dmt.network.PacketHandler;
import com.blamejared.dmt.network.messages.MessageDifficultyLevel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import hunternif.mc.impl.atlas.client.SubTile;
import hunternif.mc.impl.atlas.client.texture.TileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.blamejared.dmt.events.AAClientEventHandler.*;

@Mixin(TileTexture.class)
public abstract class TileTextureMixin {
    @Inject(at = @At(value = "HEAD"), method = "drawSubTile", remap = false)
    private void onDrawEnd(MatrixStack matrices, SubTile subtile, int tileHalfSize, CallbackInfo ci) {
        if(MSClientEventHandler.showOverlay) {
            int color = AAClientEventHandler.getColor(subtile.x, subtile.y);
            AbstractGui.fill(matrices, subtile.x * tileHalfSize, subtile.y * tileHalfSize, subtile.x * tileHalfSize + tileHalfSize, subtile.y * tileHalfSize + tileHalfSize, color);
        }
    }
}
