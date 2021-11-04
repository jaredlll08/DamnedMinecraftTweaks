package com.blamejared.dmt.mixin;

import com.blamejared.dmt.events.AAClientEventHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import hunternif.mc.impl.atlas.client.TileRenderIterator;
import hunternif.mc.impl.atlas.client.gui.GuiAtlas;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GuiAtlas.class)
public class GuiAtlasMixin {
    @Shadow private int tile2ChunkScale;

    @Shadow private double mapScale;

    @Shadow private int mapOffsetX;

    @Shadow private int mapOffsetY;

    @Inject(at = @At(value = "INVOKE", target = "hunternif/mc/impl/atlas/client/TileRenderIterator.iterator()Ljava/util/Iterator;"), method = "render", remap = false, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void onRender(MatrixStack matrices, int mouseX, int mouseY, float par3, CallbackInfo ci, long currentMillis, long deltaMillis, int mapStartX, int mapStartZ, int mapEndX, int mapEndZ, int mapStartScreenX, int mapStartScreenY, TileRenderIterator tiles, int v) {
        AAClientEventHandler.mapOffsetX = mapOffsetX;
        AAClientEventHandler.mapOffsetY = mapOffsetY;
        AAClientEventHandler.atlasStartX = mapStartX;
        AAClientEventHandler.atlasStartZ = mapStartZ;
        AAClientEventHandler.atlasTileScale = this.tile2ChunkScale;
        AAClientEventHandler.atlasMapScale = this.mapScale;
    }
}
