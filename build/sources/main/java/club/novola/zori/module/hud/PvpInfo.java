package club.novola.zori.module.hud;

import club.novola.zori.module.Module;

public class PvpInfo extends Module {
    public PvpInfo() {
        super("PvpInfo", Category.HUD);
        INSTANCE = this;
    }

    public static PvpInfo INSTANCE;
}
