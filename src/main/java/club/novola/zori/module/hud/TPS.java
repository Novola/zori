package club.novola.zori.module.hud;

import club.novola.zori.module.Module;

public class TPS extends Module {
    public TPS() {
        super("TPS", Category.HUD);
        INSTANCE = this;
    }

    public static TPS INSTANCE;
}