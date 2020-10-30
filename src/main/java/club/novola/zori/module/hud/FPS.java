package club.novola.zori.module.hud;

import club.novola.zori.module.Module;

public class FPS extends Module {
    public FPS() {
        super("FPS", Category.HUD);
        INSTANCE = this;
    }

    public static FPS INSTANCE;
}
