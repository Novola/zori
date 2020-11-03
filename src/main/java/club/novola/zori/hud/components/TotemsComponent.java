package club.novola.zori.hud.components;

import club.novola.zori.hud.HudComponent;
import club.novola.zori.module.hud.Totems;
import club.novola.zori.util.Wrapper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class TotemsComponent extends HudComponent<Totems> {
    public TotemsComponent() {
        super("Totems", 473, 453, Totems.INSTANCE);
        width = 16;
        height = 16;
    }

    private static void preitemrender() {
        GL11.glPushMatrix();
        GL11.glDepthMask(true);
        GlStateManager.clear(256);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.scale(1.0f, 1.0f, 0.01f);
    }

    private static void postitemrender() {
        GlStateManager.scale(1.0f, 1.0f, 1.0f);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GL11.glPopMatrix();
    }

    public void render() {
        int totems = Wrapper.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        if (Wrapper.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            ++totems;
        }
        final ItemStack items = new ItemStack(Items.TOTEM_OF_UNDYING, totems);
        this.itemrender(items);
    }

    private void itemrender(final ItemStack itemStack) {
        preitemrender();
        Wrapper.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, (int)this.x, (int)this.y);
        Wrapper.mc.getRenderItem().renderItemOverlays(Wrapper.mc.fontRenderer, itemStack, (int)this.x, (int)this.y);
        postitemrender();
    }
}
