package com.blamejared.dmt.mixin;

import hunternif.mc.impl.atlas.client.gui.GuiBookmarkButton;
import hunternif.mc.impl.atlas.client.texture.ITexture;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = GuiBookmarkButton.class, remap = false)
public interface GuiBookmarkButtonMixin {
    @Invoker(value = "<init>")
    static GuiBookmarkButton callInit(int colorIndex, ITexture iconTexture, ITextComponent title) {
        throw new RuntimeException("Mixin failed to load");
    }
}
