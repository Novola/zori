package club.novola.zori.hud.components;

import club.novola.zori.Zori;
import club.novola.zori.module.hud.Watermark;
import club.novola.zori.util.Wrapper;
import club.novola.zori.hud.HudComponent;

public class WatermarkComponent extends HudComponent<Watermark> {
    public WatermarkComponent() {
        super("Watermark", 2, 2, Watermark.INSTANCE);
    }

    @Override
    public void render() {
        super.render();
        Wrapper.getFontRenderer().drawStringWithShadow(Zori.getInstance().toString(), x, y, Zori.getInstance().clientSettings.getColor());
        width = Wrapper.getFontRenderer().getStringWidth(Zori.getInstance().toString());
    }
}
