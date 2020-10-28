package club.novola.zori.hud.components;

import club.novola.zori.Zori;
import club.novola.zori.module.gui.PvpInfo;
import club.novola.zori.util.Wrapper;
import club.novola.zori.hud.HudComponent;

public class PvpInfoComponent extends HudComponent<PvpInfo> {
    public PvpInfoComponent() {
        super("PvpInfo", 2, 200, PvpInfo.INSTANCE);
    }

    @Override
    public void render() {
        super.render();
        String s = Zori.getInstance().moduleManager.getEnabledColor("AutoCrystal") + "instantCrystal  " + Zori.getInstance().moduleManager.getEnabledColor("instantFeetPlace") + "instantFeetPlace  " + Zori.getInstance().moduleManager.getEnabledColor("instantTrap") + "instantTrap";
        Wrapper.getFontRenderer().drawStringWithShadow(s, x, y, -1);
        width = Wrapper.getFontRenderer().getStringWidth(s);
    }
}
