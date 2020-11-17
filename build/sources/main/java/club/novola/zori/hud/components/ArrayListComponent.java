package club.novola.zori.hud.components;

import club.novola.zori.Zori;
import club.novola.zori.hud.HudComponent;
import club.novola.zori.managers.ModuleManager;
import club.novola.zori.module.Module;
import club.novola.zori.module.client.Arraylist;
import club.novola.zori.util.ColorUtil;
import club.novola.zori.util.Wrapper;

import java.awt.*;

public class ArrayListComponent extends HudComponent<Arraylist> {

    public ArrayListComponent() {
        super("Arraylist", 2, 100, Arraylist.INSTANCE);
        width = 70;
        height = Wrapper.mc.fontRenderer.FONT_HEIGHT;
    }

    public void render() {
        super.render();
        if (Wrapper.mc.player != null && Wrapper.mc.world != null) {
            int currY = Wrapper.mc.fontRenderer.FONT_HEIGHT + 2;
            Color c = Zori.getInstance().clientSettings.getColorr(255);
            int nonrainbow = ColorUtil.toRGBA(c.getRed(), c.getGreen(), c.getBlue(), 255);
            for (Module m : ModuleManager.getEnabledModules()) {
                if (m.isEnabled()) {
                    if (m.getDrawnBool()) {
                        Wrapper.getFontRenderer().drawStringWithShadow(m.getName(), this.x, this.y + currY, nonrainbow);

                        currY += Wrapper.mc.fontRenderer.FONT_HEIGHT;
                    }
                }
            }
        }
    }
}
