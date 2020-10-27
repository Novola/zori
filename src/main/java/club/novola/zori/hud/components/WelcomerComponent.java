package club.novola.zori.hud.components;

import club.novola.zori.Zori;
import club.novola.zori.hud.HudComponent;
import club.novola.zori.module.gui.Welcomer;
import club.novola.zori.util.Wrapper;

public class WelcomerComponent extends HudComponent<Welcomer> {
    public WelcomerComponent() {
        super("Welcomer", 2, 2, Welcomer.INSTANCE);
    }

    @Override
    public void render() {
        if (Wrapper.getPlayer() != null) {
            super.render();
            Wrapper.getFontRenderer().drawStringWithShadow("Welcome, " + Wrapper.getPlayer().getName(), x, y, Zori.getInstance().clientSettings.getColor());
            width = Wrapper.getFontRenderer().getStringWidth(Zori.getInstance().toString());
        }
    }
}
