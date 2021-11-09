package com.blamejared.dmt.compat.crt;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.dmt.TweakBeamRenderer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@NativeTypeRegistration(value = TweakBeamRenderer.class, zenCodeName = "mods.dmt.TweakBeam")
public class ExpandTweakBeam {
    @ZenCodeType.Method
    public static void createBeam(WorldRenderer context, MatrixStack mat, float partialTicks, BeamObject beamObject) {
        if(EffectiveSide.get().isClient()) {

        } else {

        }
    }
}
