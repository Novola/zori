package club.novola.zori.hud.components;

import club.novola.zori.hud.HudComponent;
import club.novola.zori.module.gui.InventoryPreview;
import club.novola.zori.util.Wrapper;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

public class InventoryComponent extends HudComponent<InventoryPreview> {
    public InventoryComponent() {
        super("Inventory", 0, 0, InventoryPreview.INSTANCE);
        width = 146; // 9 * 16 = 9 columns * size
        height = 50; // 3 * 16 = 3 rows * size
    }

    @Override
    public void render() {
        super.render();
        if(Wrapper.getPlayer() == null || Wrapper.getPlayer().inventory == null) return;

		// draw background
        switch(module.background.getValue()){
            // TODO
            case CLEAR:
            case NORMAL:
            case TRANS:
                int i1 = height / 5;
                int i2 = i1 * 2;
                int i3 = i1 * 3;
                int i4 = i1 * 4;
                int i5 = i1 * 5;

                int cyan = 0xaa5bcefa;
                int pink = 0xaaf5a9b8;
                int white = 0xaaffffff;

                GlStateManager.enableAlpha();
                Gui.drawRect(x, y, x + width, y + i1, cyan);
                Gui.drawRect(x, y + i1, x + width, y + i2, pink);
                Gui.drawRect(x, y + i2, x + width, y + i3, white);
                Gui.drawRect(x, y + i3, x + width, y + i4, pink);
                Gui.drawRect(x, y + i4, x + width, y + i5, cyan);
                GlStateManager.disableAlpha();
				
				break;
        }
		
		// draw items

        int slotX = 0;
        int slotY = 0;

        RenderHelper.enableGUIStandardItemLighting();
        for(ItemStack stack : Wrapper.getPlayer().inventory.mainInventory){
            if(Wrapper.getPlayer().inventory.mainInventory.indexOf(stack) < 9) continue; // hotbar
            Wrapper.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x + slotX, y + slotY);
            Wrapper.mc.getRenderItem().renderItemOverlays(Wrapper.getFontRenderer(), stack, x + slotX, y + slotY);
            if(slotX < (8 * 16)) {
                slotX += 16;
            } else {
                slotX = 0;
                slotY += 16;
            }
        }
        RenderHelper.disableStandardItemLighting();
    }
}
