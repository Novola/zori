package club.novola.zori.module.hud;

import club.novola.zori.module.Module;

public class Watermark extends Module {
    public Watermark() {
        super("Watermark", Category.HUD);
        INSTANCE = this;
    }

    public static Watermark INSTANCE;
}
