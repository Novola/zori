package club.novola.zori.hud.components;

import club.novola.zori.hud.HudComponent;
import club.novola.zori.module.hud.ArmorHUD;
import club.novola.zori.util.ColorUtil;
import club.novola.zori.util.Wrapper;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class ArmorHUDComponent extends HudComponent<ArmorHUD> {

    private int offHandHeldItemCount;
    private int armourCompress;
    private int armourSpacing;

    public ArmorHUDComponent() {
        super("ArmorHUD", 300, 300, ArmorHUD.INSTANCE);
        width = 0;
        height = 0;
    }

    public void render() {
        ScaledResolution resolution = new ScaledResolution(Wrapper.mc);
        RenderItem itemRender = Wrapper.mc.getRenderItem();

        GlStateManager.enableTexture2D();
        int i = resolution.getScaledWidth() / 2;
        int iteration = 0;
        int y = resolution.getScaledHeight() - 55 - (Wrapper.mc.player.isInWater() ? 10 : 0);

        for (ItemStack is : Wrapper.mc.player.inventory.armorInventory) {

            iteration++;
            if (is.isEmpty()) continue;
            int x = i - 90 + (9 - iteration) * armourSpacing + armourCompress;
            GlStateManager.enableDepth();

            itemRender.zLevel = 200F;
            itemRender.renderItemAndEffectIntoGUI(is, x, y);
            itemRender.renderItemOverlayIntoGUI(Wrapper.mc.fontRenderer, is, x, y, "");
            itemRender.zLevel = 0F;

            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();

            String s = is.getCount() > 1 ? is.getCount() + "" : "";
            Wrapper.mc.fontRenderer.drawStringWithShadow(s, x + 19 - 2 - Wrapper.mc.fontRenderer.getStringWidth(s), y + 9, 0xffffff);

            if (module.damageA.getValue()) {
                float green = ((float) is.getMaxDamage() - (float) is.getItemDamage()) / (float) is.getMaxDamage();
                float red = 1 - green;
                int dmg = 100 - (int) (red * 100);
                Wrapper.mc.fontRenderer.drawStringWithShadow(dmg + "", x + 8 - Wrapper.mc.fontRenderer.getStringWidth(dmg + "") / 2, y - 11, ColorUtil.toRGBA((int)(red * 255.0f), (int)(green * 255.0f), 0));
            }

            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
        }

        if (module.extraInfo.getValue()) {
            for (ItemStack is : Wrapper.mc.player.inventory.offHandInventory) {
                Item helfInOffHand = Wrapper.mc.player.getHeldItemOffhand().getItem();
                offHandHeldItemCount = getItemsOffHand(helfInOffHand);
                GlStateManager.pushMatrix();
                GlStateManager.disableAlpha();
                GlStateManager.clear(256);
                GlStateManager.enableBlend();
                GlStateManager.pushAttrib();
                RenderHelper.enableGUIStandardItemLighting();
                GlStateManager.disableDepth();

                Wrapper.mc.getRenderItem().renderItemAndEffectIntoGUI(is, 572, y);
                itemRender.renderItemOverlayIntoGUI(Wrapper.mc.fontRenderer, is, 572, y, String.valueOf(offHandHeldItemCount));
                GlStateManager.enableDepth();
                RenderHelper.disableStandardItemLighting();
                GlStateManager.popAttrib();
                GlStateManager.disableBlend();

                GlStateManager.disableDepth();
                GlStateManager.disableLighting();
                GlStateManager.enableDepth();
                GlStateManager.enableAlpha();
                GlStateManager.popMatrix();

            }
        }

        if (module.extraInfo.getValue()) {
            Item currentHeldItem = Wrapper.mc.player.inventory.getCurrentItem().getItem();
            int currentHeldItemCount = Wrapper.mc.player.inventory.getCurrentItem().getCount();

            ItemStack stackHeld = new ItemStack(currentHeldItem, 1);
            GlStateManager.pushMatrix();
            GlStateManager.disableAlpha();
            GlStateManager.clear(256);
            GlStateManager.enableBlend();
            GlStateManager.pushAttrib();
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.disableDepth();
            Wrapper.mc.getRenderItem().renderItemAndEffectIntoGUI(stackHeld, 556, y);

            itemRender.renderItemOverlayIntoGUI(Wrapper.mc.fontRenderer, stackHeld, 556, y, String.valueOf(currentHeldItemCount));

            GlStateManager.enableDepth();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popAttrib();
            GlStateManager.disableBlend();

            GlStateManager.disableDepth();
            GlStateManager.disableLighting();
            GlStateManager.enableDepth();
            GlStateManager.enableAlpha();
            GlStateManager.popMatrix();

        }

        GlStateManager.enableDepth();
        GlStateManager.disableLighting();

        if (module.extraInfo.getValue()) {
            armourCompress = 14;
            armourSpacing = 17;
        } else {
            armourCompress = 2;
            armourSpacing = 20;
        }

        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
    }

    int getItemsOffHand(Item i) {
        return Wrapper.mc.player.inventory.offHandInventory.stream().
                filter(itemStack -> itemStack.getItem() == i).mapToInt(ItemStack::getCount).sum();
    }

}
