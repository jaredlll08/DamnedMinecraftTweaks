package com.blamejared.dmt;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

import java.awt.*;

public class TweakBeamRenderer extends RenderState {

    private static final ResourceLocation TWEAK_BEAM_TEXTURE = new ResourceLocation("dmt", "textures/entity/tweak_beam.png");
    private static final RenderType TWEAK_BEAM_RENDERTYPE = createRenderType();

    public TweakBeamRenderer(String string, Runnable run, Runnable run2) {
        super(string, run, run2);
    }

    public static void renderTweakBeam(MatrixStack stack, IRenderTypeBuffer buffer, float pticks, long worldtime, ItemEntity item) {
        float beamRadius = 0.05f * 0.65f;
        float glowRadius = beamRadius + (beamRadius * 0.2f);
        float beamAlpha = 1.0f;
        float beamHeight = 0.25f;
        float yOffset = 0.0f;

        Color color = getItemColor(item);
        float R = color.getRed() / 255f;
        float G = color.getGreen() / 255f;
        float B = color.getBlue() / 255f;

        //I will rewrite the beam rendering code soon! I promise!

        stack.pushPose();

        //Render main beam
        stack.pushPose();
        float rotation = (float) Math.floorMod(worldtime, 40L) + pticks;
        stack.mulPose(Vector3f.YP.rotationDegrees(rotation * 2.25F - 45.0F));
        stack.translate(0, yOffset, 0);
        stack.translate(0, 1, 0);
        stack.mulPose(Vector3f.XP.rotationDegrees(180));
        renderPart(stack, buffer.getBuffer(TWEAK_BEAM_RENDERTYPE), R, G, B, beamAlpha, beamHeight, 0.0F, beamRadius, beamRadius, 0.0F, -beamRadius, 0.0F, 0.0F, -beamRadius);
        stack.mulPose(Vector3f.XP.rotationDegrees(-180));
        renderPart(stack, buffer.getBuffer(TWEAK_BEAM_RENDERTYPE), R, G, B, beamAlpha, beamHeight, 0.0F, beamRadius, beamRadius, 0.0F, -beamRadius, 0.0F, 0.0F, -beamRadius);
        stack.popPose();

        //Render glow around main beam
        stack.translate(0, yOffset, 0);
        stack.translate(0, 1, 0);
        stack.mulPose(Vector3f.XP.rotationDegrees(180));
        renderPart(stack, buffer.getBuffer(TWEAK_BEAM_RENDERTYPE), R, G, B, beamAlpha * 0.4f, beamHeight, -glowRadius, -glowRadius, glowRadius, -glowRadius, -beamRadius, glowRadius, glowRadius, glowRadius);
        stack.mulPose(Vector3f.XP.rotationDegrees(-180));
        renderPart(stack, buffer.getBuffer(TWEAK_BEAM_RENDERTYPE), R, G, B, beamAlpha * 0.4f, beamHeight, -glowRadius, -glowRadius, glowRadius, -glowRadius, -beamRadius, glowRadius, glowRadius, glowRadius);

        stack.popPose();
    }
    // Copied from lootbeams code
    private static Color getItemColor(ItemEntity item) {

        try {

            //From Config Overrides

            //From NBT

            //From Name

            //From Rarity
            if (item.getItem().getRarity().color != null) {
                return new Color(item.getItem().getRarity().color.getColor());
            } else {
                return Color.WHITE;
            }
        } catch (Exception e) {
            return Color.WHITE;
        }
    }
    private static void renderPart(MatrixStack stack, IVertexBuilder builder, float red, float green, float blue, float alpha, float height, float radius_1, float radius_2, float radius_3, float radius_4, float radius_5, float radius_6, float radius_7, float radius_8) {
        MatrixStack.Entry matrixentry = stack.last();
        Matrix4f matrixpose = matrixentry.pose();
        Matrix3f matrixnormal = matrixentry.normal();
        renderQuad(matrixpose, matrixnormal, builder, red, green, blue, alpha, height, radius_1, radius_2, radius_3, radius_4);
        renderQuad(matrixpose, matrixnormal, builder, red, green, blue, alpha, height, radius_7, radius_8, radius_5, radius_6);
        renderQuad(matrixpose, matrixnormal, builder, red, green, blue, alpha, height, radius_3, radius_4, radius_7, radius_8);
        renderQuad(matrixpose, matrixnormal, builder, red, green, blue, alpha, height, radius_5, radius_6, radius_1, radius_2);
    }
    private static void renderQuad(Matrix4f pose, Matrix3f normal, IVertexBuilder builder, float red, float green, float blue, float alpha, float y, float z1, float texu1, float z, float texu) {
        addVertex(pose, normal, builder, red, green, blue, alpha, y, z1, texu1, 1f, 0f);
        addVertex(pose, normal, builder, red, green, blue, alpha, 0f, z1, texu1, 1f, 1f);
        addVertex(pose, normal, builder, red, green, blue, alpha, 0f, z, texu, 0f, 1f);
        addVertex(pose, normal, builder, red, green, blue, alpha, y, z, texu, 0f, 0f);
    }
    private static void addVertex(Matrix4f pose, Matrix3f normal, IVertexBuilder builder, float red, float green, float blue, float alpha, float y, float x, float z, float texu, float texv) {
        builder.vertex(pose, x, y, z).color(red, green, blue, alpha).uv(texu, texv).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
    }
    private static RenderType createRenderType() {
        RenderType.State state = RenderType.State.builder().setTextureState(new RenderState.TextureState(TWEAK_BEAM_TEXTURE, false, false)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setWriteMaskState(RenderState.COLOR_WRITE).setFogState(NO_FOG).createCompositeState(false);
        return RenderType.create("tweak_beam", DefaultVertexFormats.BLOCK, 7, 256, false, true, state);
    }

}
