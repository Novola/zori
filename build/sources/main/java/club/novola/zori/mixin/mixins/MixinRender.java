package club.novola.zori.mixin.mixins;

import club.novola.zori.util.Wrapper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// fucked around with nametags, this mixin is not actually registered so it doesnt do anything
@Mixin(Render.class)
public abstract class MixinRender<T extends Entity> {
    @Shadow
    @Final
    protected RenderManager renderManager;

    @Inject(method = "renderLivingLabel", at=@At("HEAD"), cancellable = true)
    private void renderLivingLabel(T entityIn, String str, double x, double y, double z, int maxDistance, CallbackInfo ci){
        /*
        if(Zori.getInstance().moduleManager.getModuleByName("NameTags").isEnabled() && entityIn instanceof EntityPlayer){
            ci.cancel();
            //double d0 = entityIn.getDistanceSq(this.renderManager.renderViewEntity);

            //if (d0 <= (double)(maxDistance * maxDistance))
            //{
                boolean flag = entityIn.isSneaking();
                float f = this.renderManager.playerViewY;
                float f1 = this.renderManager.playerViewX;
                boolean flag1 = this.renderManager.options.thirdPersonView == 2;
                float f2 = entityIn.height + 0.5F - (flag ? 0.25F : 0.0F);
                int i = "deadmau5".equals(str) ? -10 : 0;
                str = entityIn.getName() + " " + (int)(((EntityPlayer) entityIn).getHealth() + ((EntityPlayer) entityIn).getAbsorptionAmount());
                drawNameplate(this.renderManager.getFontRenderer(), str, (float)x, (float)y + f2, (float)z, i, f, f1, flag1, flag);
            //}
        }
         */
    }

    private void drawNameplate(FontRenderer fontRendererIn, String str, float x, float y, float z, int verticalShift, float viewerYaw, float viewerPitch, boolean isThirdPersonFrontal, boolean isSneaking)
    {
		//GlStateManager.disableCull();
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-viewerYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float)(isThirdPersonFrontal ? -1 : 1) * viewerPitch, 1.0F, 0.0F, 0.0F);
        float scale = 0.025f;
        if(Wrapper.mc.getRenderViewEntity() != null && (Wrapper.mc.getRenderViewEntity().getDistance(x, y, z) / -10) < -1)
            scale = (float) (0.025f * (Wrapper.mc.getRenderViewEntity().getDistance(x, y, z) / -10));
        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.disableLighting();
        //GlStateManager.depthMask(false);

        //if (!isSneaking)
        //{
            //GlStateManager.disableDepth();
        //}


        //GlStateManager.enableBlend();
        //GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        //int i = fontRendererIn.getStringWidth(str) / 2;
        //GlStateManager.disableTexture2D();
        //Tessellator tessellator = Tessellator.getInstance();
        //BufferBuilder bufferbuilder = tessellator.getBuffer();
        //bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        //bufferbuilder.pos((double)(-i - 1), (double)(-1 + verticalShift), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        //bufferbuilder.pos((double)(-i - 1), (double)(8 + verticalShift), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        //bufferbuilder.pos((double)(i + 1), (double)(8 + verticalShift), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        //bufferbuilder.pos((double)(i + 1), (double)(-1 + verticalShift), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        //tessellator.draw();
        //GlStateManager.enableTexture2D();

        //if (!isSneaking)
        //{
            //GlStateManager.color(1, 1, 1, 1);
            //fontRendererIn.drawStringWithShadow(str, -fontRendererIn.getStringWidth(str) / 2f, verticalShift, 553648127);
            //GlStateManager.enableDepth();
        //}

        //GlStateManager.enableDepth();
        //GlStateManager.depthMask(true);
        //fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2, verticalShift, isSneaking ? 553648127 : -1);
        //GlStateManager.color(1, 1, 1, 1);
        fontRendererIn.drawStringWithShadow(str, -fontRendererIn.getStringWidth(str) / 2f, verticalShift, isSneaking ? 0xffff00 : -1);
        GlStateManager.enableLighting();
        //GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }
}
