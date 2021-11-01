package com.blamejared.dmt.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.robertx22.age_of_exile.uncommon.utilityclasses.HealthUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.torocraft.torohealth.bars.HealthBarRenderer;
import org.spongepowered.asm.mixin.Mixin;
import net.torocraft.torohealth.display.BarDisplay;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BarDisplay.class)
public class ToroHealthMixin {
    private Minecraft mc;
    /**
    *
    @author Witixin
     */
    @Overwrite(aliases = "draw", remap = false)
    public void draw(MatrixStack matrix, LivingEntity entity) {
        try {
            BarDisplay mix = ((BarDisplay) (Object)this);
            mix.getClass().getDeclaredField("mc").setAccessible(true);
            mc  =(Minecraft) mix.getClass().getDeclaredField("mc").get(mix);
            if (mc == null){
                System.out.println("Mc is null");
                return;
            }
            if (mc.font == null){
                System.out.println("Font is null");
                return;
            }
            int xOffset = 0;
            GlStateManager._blendColor(1.0F, 1.0F, 1.0F, 1.0F);
            HealthBarRenderer.render(matrix, entity, 63.0D, 14.0D, 130.0F, false);
            String name = entity.getDisplayName().getString();
            int healthMax = MathHelper.ceil(HealthUtils.getMaxHealth(entity));
            int healthCur = HealthUtils.getCurrentHealth(entity);
            String healthText = healthCur + "/" + healthMax;
            GlStateManager._color4f(1.0F, 1.0F, 1.0F, 1.0F);
            mix.drawStringWithShadow(matrix, mc.font, name, xOffset, 2, 16777215);
            mix.drawWithShadow(matrix, name, (float)xOffset, 2.0F, 16777215);
            xOffset = xOffset + this.mc.font.width(name) + 5;
            ObfuscationReflectionHelper.findMethod(BarDisplay.class, "renderHeartIcon", MatrixStack.class, int.class, int.class).setAccessible(true);
            ObfuscationReflectionHelper.findMethod(BarDisplay.class, "renderHeartIcon", MatrixStack.class, int.class, int.class).invoke(mix, matrix, xOffset, 1);
            xOffset += 10;
            mix.drawWithShadow(matrix, healthText, (float)xOffset, 2.0F, 14737632);
            xOffset += mc.font.width(healthText) + 5;
            int armor = entity.getArmorValue();
            if (armor > 0) {
                ObfuscationReflectionHelper.findMethod(BarDisplay.class, "renderArmorIcon", MatrixStack.class, int.class, int.class).setAccessible(true);
                ObfuscationReflectionHelper.findMethod(BarDisplay.class, "renderArmorIcon", MatrixStack.class, int.class, int.class).invoke(mix, matrix, xOffset, 1);
                xOffset += 10;
                mix.drawWithShadow(matrix, armor + "", (float)xOffset, 2.0F, 14737632);
            }
        }
        catch (Exception e){

        }

    }
}
