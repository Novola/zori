package club.novola.zori.hud.components;

import club.novola.zori.Zori;
import club.novola.zori.module.hud.PvpInfo;
import club.novola.zori.util.Wrapper;
import club.novola.zori.hud.HudComponent;

public class PvpInfoComponent extends HudComponent<PvpInfo> {
    public PvpInfoComponent() {
        super("PvpInfo", 2, 200, PvpInfo.INSTANCE);
    }

    @Override
    public void render() {
        super.render();
        String s = Zori.getInstance().moduleManager.getEnabledColor("AutoCrystal") + "CA  " + Zori.getInstance().moduleManager.getEnabledColor("Surround") + "SU  " + Zori.getInstance().moduleManager.getEnabledColor("AutoTrap") + "AT";
        Wrapper.getFontRenderer().drawStringWithShadow(s, x, y, -1);
        width = Wrapper.getFontRenderer().getStringWidth(s);
    }
}
