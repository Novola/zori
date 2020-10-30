package club.novola.zori.module.hud;

import club.novola.zori.module.Module;

public class OffhandMode extends Module {
    public OffhandMode() {
        super("OffhandMode", Category.HUD);
        INSTANCE = this;
    }

    public static OffhandMode INSTANCE;
}
