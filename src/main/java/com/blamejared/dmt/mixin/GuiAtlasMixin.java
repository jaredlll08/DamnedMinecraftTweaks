package com.blamejared.dmt.mixin;

import com.blamejared.dmt.events.AAClientEventHandler;
import com.blamejared.dmt.events.MSClientEventHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import hunternif.mc.impl.atlas.client.TileRenderIterator;
import hunternif.mc.impl.atlas.client.gui.GuiAtlas;
import hunternif.mc.impl.atlas.client.gui.GuiBookmarkButton;
import hunternif.mc.impl.atlas.client.gui.core.GuiComponent;
import hunternif.mc.impl.atlas.client.texture.IconTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GuiAtlas.class)
public class GuiAtlasMixin extends GuiComponent {
    @Shadow private int tile2ChunkScale;
    @Shadow private double mapScale;
    @Shadow private int mapOffsetX;
    @Shadow private int mapOffsetY;

    private GuiBookmarkButton toggleOverlayButton = GuiBookmarkButtonMixin.callInit(0, new IconTexture(new ResourceLocation("damnedminecrafttweaks", "textures/overlaybutton.png")), new TranslationTextComponent("gui.damnedminecrafttweaks.toggleOverlay"));

    @Inject(at = @At(value = "RETURN"), method = "<init>")
    private void onInit(CallbackInfo ci) {
        this.addChild(toggleOverlayButton).offsetGuiCoords(300, 98);
        toggleOverlayButton.addListener(button -> MSClientEventHandler.showOverlay = !MSClientEventHandler.showOverlay);
    }

    @Inject(at = @At(value = "INVOKE", target = "hunternif/mc/impl/atlas/client/TileRenderIterator.iterator()Ljava/util/Iterator;"), method = "render", locals = LocalCapture.CAPTURE_FAILSOFT, remap = false)
    private void onRender(MatrixStack matrices, int mouseX, int mouseY, float par3, CallbackInfo ci, long currentMillis, long deltaMillis, int mapStartX, int mapStartZ, int mapEndX, int mapEndZ, int mapStartScreenX, int mapStartScreenY, TileRenderIterator tiles, int v) {
        AAClientEventHandler.mapOffsetX = mapOffsetX;
        AAClientEventHandler.mapOffsetY = mapOffsetY;
        AAClientEventHandler.atlasStartX = mapStartX;
        AAClientEventHandler.atlasStartZ = mapStartZ;
        AAClientEventHandler.atlasTileScale = this.tile2ChunkScale;
        AAClientEventHandler.atlasMapScale = this.mapScale;
    }
}
