package club.novola.zori.module.render;

import club.novola.zori.Zori;
import club.novola.zori.module.Module;
import club.novola.zori.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VoidESP extends Module {

    Minecraft mc = Minecraft.getMinecraft();

    private Setting<Integer> range = register("Range", 10, 1, 20);
    private Setting<Integer> red = register("Red", 10, 0, 255);
    private Setting<Integer> green = register("Green", 100, 0, 255);
    private Setting<Integer> blue = register("Blue", 200, 0, 255);
    private Setting<Boolean> sync = register("Sync", false);

    public VoidESP() {
        super("VoidESP", Category.RENDER);
    }

    public final List<BlockPos> void_blocks = new ArrayList<>();

    private final ICamera camera = new Frustum();

    public void onUpdate() {

        if (mc.player == null) return;

        void_blocks.clear();

        final Vec3i player_pos = new Vec3i(mc.player.posX, mc.player.posY, mc.player.posZ);

        for (int x = player_pos.getX() - range.getValue().intValue(); x < player_pos.getX() + range.getValue(); x++) {
            for (int z = player_pos.getZ() - range.getValue().intValue(); z < player_pos.getZ() + range.getValue(); z++) {
                for (int y = player_pos.getY() + range.getValue().intValue(); y > player_pos.getY() - range.getValue(); y--) {
                    final BlockPos blockPos = new BlockPos(x, y, z);

                    if (is_void_hole(blockPos))
                        void_blocks.add(blockPos);
                }
            }
        }
    }

    public boolean is_void_hole(BlockPos blockPos) {
        if (blockPos.getY() != 0)
            return false;

        if (mc.world.getBlockState(blockPos).getBlock() != Blocks.AIR)
            return false;

        return true;

    }

    @Override
    public void onRender3D() {
        new ArrayList<>(void_blocks).forEach(pos -> {

            final AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - mc.getRenderManager().viewerPosX, pos.getY() - mc.getRenderManager().viewerPosY,
                    pos.getZ() - mc.getRenderManager().viewerPosZ, pos.getX() + 1 - mc.getRenderManager().viewerPosX, pos.getY() + 1 - mc.getRenderManager().viewerPosY,
                    pos.getZ() + 1 - mc.getRenderManager().viewerPosZ);

            camera.setPosition(mc.getRenderViewEntity().posX, mc.getRenderViewEntity().posY, mc.getRenderViewEntity().posZ);

            if (camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + mc.getRenderManager().viewerPosX, bb.minY + mc.getRenderManager().viewerPosY, bb.minZ + mc.getRenderManager().viewerPosZ,
                    bb.maxX + mc.getRenderManager().viewerPosX, bb.maxY + mc.getRenderManager().viewerPosY, bb.maxZ + mc.getRenderManager().viewerPosZ)))
            {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.disableDepth();
                GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                GlStateManager.disableTexture2D();
                GlStateManager.depthMask(false);
                GL11.glEnable(GL11.GL_LINE_SMOOTH);
                GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
                GL11.glLineWidth(1.5f);

                if(!sync.getValue()){
                    RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, red.getValue() / 255f, green.getValue() / 255f, blue.getValue() / 255f, 0.50f);
                    RenderGlobal.renderFilledBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, red.getValue() / 255f, green.getValue() / 255f, blue.getValue() / 255f, 0.22f);
                }else {
                    Color c = Zori.getInstance().clientSettings.getColorr(255);
                    RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, 0.50f);
                    RenderGlobal.renderFilledBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, 0.22f);
                }
                GL11.glDisable(GL11.GL_LINE_SMOOTH);
                GlStateManager.depthMask(true);
                GlStateManager.enableDepth();
                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
        });
    }
}
