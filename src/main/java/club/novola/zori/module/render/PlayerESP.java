package club.novola.zori.module.render;

import club.novola.zori.module.Module;
import club.novola.zori.util.EntityUtils;
import club.novola.zori.util.RenderUtils;
import club.novola.zori.util.Wrapper;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

// doesnt work
public class PlayerESP extends Module {
    public PlayerESP() {
        super("PlayerESP", Category.RENDER);
    }

    @Override
    public void onRender3D(){
        for(EntityPlayer player : Wrapper.getWorld().playerEntities){
            Vec3d pos = EntityUtils.INSTANCE.getInterpolatedPos(player);
            RenderUtils.INSTANCE.prepare();
            GlStateManager.pushMatrix();
            GlStateManager.translate(pos.x, pos.y, pos.z);
            GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-Wrapper.mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate((float)(Wrapper.mc.getRenderManager().options.thirdPersonView == 2 ? -1 : 1) * Wrapper.mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();
            buffer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
            GlStateManager.glLineWidth(1f);
            buffer.pos(pos.x, pos.y, pos.z).color(0f, 0f, 0f, 1f).endVertex();
            buffer.pos(pos.x + player.width, pos.y, pos.z).color(0f, 0f, 0f, 1f).endVertex();
            buffer.pos(pos.x + player.width, pos.y + player.height, pos.z).color(0f, 0f, 0f, 1f).endVertex();
            buffer.pos(pos.x, pos.y + player.height, pos.z).color(0f, 0f, 0f, 1f).endVertex();
            buffer.pos(pos.x, pos.y, pos.z).color(0f, 0f, 0f, 1f).endVertex();
            tessellator.draw();
            GlStateManager.popMatrix();
            RenderUtils.INSTANCE.finish();
        }
    }
}
