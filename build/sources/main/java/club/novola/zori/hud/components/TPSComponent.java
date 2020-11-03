package club.novola.zori.hud.components;

import club.novola.zori.Zori;
import club.novola.zori.hud.HudComponent;
import club.novola.zori.module.hud.TPS;
import club.novola.zori.util.TickRate;
import club.novola.zori.util.Wrapper;

public class TPSComponent extends HudComponent<TPS> {
    public TPSComponent() {
        super("TPS", 2, 2, TPS.INSTANCE);
    }

    @Override
    public void renderInGui(int mouseX, int mouseY) {
        super.renderInGui(mouseX, mouseY);
            Wrapper.getFontRenderer().drawStringWithShadow("TPS", x, y, -1);
    }

    @Override
    public void render() {
        super.render();
        Wrapper.getFontRenderer().drawStringWithShadow("TPS: " + "\u00A7f" + TickRate.TPS, x, y, Zori.getInstance().clientSettings.getColor());
        width = Wrapper.getFontRenderer().getStringWidth(Zori.getInstance().toString());
    }
}